package group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.AuthUtils;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.service.IHotelService;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.po.IsolationInfoPO;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model.vo.IsolationInfoVO;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.service.IsolationInfoService;
import group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.dao.IsolationInfoMapper;

import group.oneonetwo.hotelintelligencesystem.modules.room.model.po.RoomPO;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.RoomVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.service.IRoomService;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.po.UserPO;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.IUserService;
import group.oneonetwo.hotelintelligencesystem.tools.WStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
* @author 文
* @description 针对表【isolation_info】的数据库操作Service实现
* @createDate 2022-04-19 10:26:02
*/
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class IsolationInfoServiceImpl implements IsolationInfoService{

    @Autowired
    IsolationInfoMapper isolationInfoMapper;

    @Autowired
    AuthUtils authUtils;

    @Autowired
    IUserService userService;

    @Autowired
    IHotelService hotelService;


    @Autowired
    IRoomService roomService;

    @Override
    public IsolationInfoVO add(IsolationInfoVO isolationInfoVO) {
        if(isolationInfoVO==null){
            throw new SavaException("插入隔离人员信息失败:实体为空");
        }
        IsolationInfoPO isolationInfoPO = new IsolationInfoPO();
        BeanUtils.copyProperties(isolationInfoVO,isolationInfoPO);
        int insert=isolationInfoMapper.insert(isolationInfoPO);
        if(insert>0){
            return isolationInfoVO;
        }
        throw new SavaException("插入隔离人员信息失败");

    }

    @Override
    public IsolationInfoVO save(IsolationInfoVO isolationInfoVO) {
        if (isolationInfoVO==null){
            throw new CommonException(501,"隔离人员信息实体为空");
        }
        IsolationInfoVO check=selectOneByIdReturnVO(isolationInfoVO.getId());
        if(check==null){
            throw new CommonException(4004,"找不到id为:"+isolationInfoVO.getId()+"的数据");
        }
        if (check.getStatus() == 1 || check.getStatus() == 2) {
            if (isolationInfoVO.getStatus() == 0) {
                throw new CommonException("请重新增加隔离人员数据!");
            }
        }else {
            if (isolationInfoVO.getStatus() != 0) {
                String roomId = check.getRoomId();
                roomService.leaveIsolationRoom(roomId);
                isolationInfoVO.setCheckOutTime(new Date());
            }
        }

        IsolationInfoPO isolationInfoPO = new IsolationInfoPO();
        BeanUtils.copyProperties(isolationInfoVO,isolationInfoPO);
        int save=isolationInfoMapper.updateById(isolationInfoPO);
        if(save>0){
            return isolationInfoVO;
        }
        throw new SavaException("更改隔离人员信息失败");

    }

    @Override
    public Integer deleteById(String id) {
        IsolationInfoVO check = selectOneByIdReturnVO(id);
        if(check==null){
            throw  new CommonException(4004,"找不到id为"+id+"的数据");
        }
        int i=isolationInfoMapper.deleteById(id);
        return i;

    }

    @Override
    public IsolationInfoVO selectOneByIdReturnVO(String id) {
        if(id==null){
            throw new CommonException(501,"参数为空");
        }
        IsolationInfoPO isolationInfoPO = isolationInfoMapper.selectById(id);
        IsolationInfoVO isolationInfoVO = new IsolationInfoVO();
        if(isolationInfoPO!=null){
            BeanUtils.copyProperties(isolationInfoPO,isolationInfoVO);
        }
        return isolationInfoVO;
    }

    @Override
    public IsolationInfoPO selectOneById(String id) {
        IsolationInfoPO isolationInfoPO = isolationInfoMapper.selectById(id);
        return isolationInfoPO;
    }

    @Override
    public Page<IsolationInfoVO> getPage(IsolationInfoVO isolationInfoVO) {
        Page<IsolationInfoPO> page=new Page<>(isolationInfoVO.getPage().getPage(),isolationInfoVO.getPage().getSize());
        return isolationInfoMapper.getPage(page,isolationInfoVO);
    }

    @Override
    public List<IsolationInfoVO> getAllList(IsolationInfoVO isolationInfoVO) {
        String authority = authUtils.getRole();
        switch (authority) {
            case "admin":break;
            case "hotel_admin":
                UserVO userVO = userService.selectOneByIdReturnVO(authUtils.getUid());
                if (userVO == null) {
                    throw new CommonException(401,"无权限");
                }
                HotelVO hotelVO = new HotelVO();
                hotelVO.setDeptId(userVO.getDept());
                hotelVO = hotelService.selectOneByDeptId(userVO.getDept());
                if (hotelVO == null) {
                    throw new CommonException(500,"该用户未绑定酒店");
                }
                isolationInfoVO.setHotelId(hotelVO.getId());
                break;
            default:
                throw new CommonException(401,"无权限");
        }
        return isolationInfoMapper.getAllList(isolationInfoVO);
    }

    @Override
    public Page<IsolationInfoVO> getOwnPage(IsolationInfoVO isolationInfoVO) {
        String uid = authUtils.getUid();
        isolationInfoVO.setuId(uid);
        return getPage(isolationInfoVO);
    }



    @Override
    public IsolationInfoPO selectOneByRoomId(String roomId) {
        QueryWrapper<IsolationInfoPO> wrapper = new QueryWrapper<>();
        wrapper.eq("room_id",roomId).eq("status",0);
        IsolationInfoPO isolationInfoPO = isolationInfoMapper.selectOne(wrapper);
        return isolationInfoPO;
    }


    @Override
    public void distribution(IsolationInfoVO isolationInfoVO) {
        RoomVO roomVO = roomService.isolationCheckIn(isolationInfoVO.getHotelId(), isolationInfoVO.getRoomType(), null);
        if(!WStringUtils.isBlank(isolationInfoVO.getUserName())) {
            UserPO username = userService.findByUsername(isolationInfoVO.getUserName());
            isolationInfoVO.setuId(username.getId());
        }
        isolationInfoVO.setStatus(0);
        isolationInfoVO.setRoomId(roomVO.getId());
        isolationInfoVO.setRoomName(roomVO.getName());
        add(isolationInfoVO);
    }

    @Override
    public IsolationInfoVO selectByRoomIdAndStaus(String roomId) {
        QueryWrapper<IsolationInfoPO> wrapper = new QueryWrapper<>();
        wrapper.eq("room_id",roomId);
        wrapper.eq("status",0);
        List<IsolationInfoPO> isolationInfoPOS = isolationInfoMapper.selectList(wrapper);
        IsolationInfoVO isolationInfoVO = new IsolationInfoVO();
        BeanUtils.copyProperties(isolationInfoPOS.get(0),isolationInfoVO);
        return isolationInfoVO;
    }

    @Override
    public IsolationInfoVO test() {
        String []province={"北京市","天津市","上海市","重庆市","河北省","山西省","辽宁省","吉林省","黑龙江省","江苏省",
                "浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省","广东省","海南省",
                "四川省","贵州省","云南省","陕西省","甘肃省","青海省","台湾省","内蒙古自治区","广西壮族自治区","西藏自治区","宁夏回族自治区"
                ,"新疆维吾尔自治区","香港特别行政区","澳门特别行政区"};

        String [][]city= {
                {"北京市"},
                {"天津市"},
                {"上海市"},
                {"重庆市"},
                {"保定市", "沧州市", "邯郸市", "唐山市", "承德市", "邢台市", "石家庄市", "衡水市", "廊坊市", "张家口市", "秦皇岛市"},//河北省11个
                {"大同市", "忻州市", "阳泉市", "长治市", "晋中市", "临汾市", "运城市", "朔州市", "太原市", "晋城市", "吕梁市"},//山西省11个
                {"本溪市", "沈阳市", "朝阳市", "大连市", "阜新市", "辽阳市", "盘锦市", "营口市", "抚顺市", "锦州市", "鞍山市", "丹东市", "葫芦岛市", "铁岭市"},//辽宁省14个
                {"长春市", "白城市", "辽源市", "通化市", "延边朝鲜族自治州", "白山市", "吉林市", "四平市", "松原市"},//吉林省9个
                {"大庆市", "大兴安岭地区", "牡丹江市", "齐齐哈尔市", "绥化市", "鸡西市", "双鸭山市", "哈尔滨市", "鹤岗市", "七台河市", "伊春市", "佳木斯市", "黑河市"},//黑龙江省13个
                {"常州市", "徐州市", "南京市", "淮安市", "南通市", "宿迁市", "无锡市", "扬州市", "盐城市", "苏州市", "泰州市", "镇江市", "连云港市"},//江苏省13个

                {"杭州市", "湖州市", "嘉兴市", "金华市", "丽水市", "宁波市", "衢州市", "绍兴市", "台州市", "温州市", "舟山市"},//浙江省11个
                {"合肥市", "蚌埠市", "阜阳市", "淮北市", "六安市", "马鞍山市", "滁州市", "宿州市", "淮南市", "安庆市", "池州市", "亳州市", "黄山市", "宣城市", "芜湖市", "铜陵市"},//安徽省16个
                {"福州市", "宁德市", "泉州市", "厦门市", "莆田市", "南平市", "龙岩市", "三明市", "漳州市"},//福建省9个
                {"南昌市", "赣州市", "吉安市", "萍乡市", "宜春市", "新余市", "景德镇市", "抚州市", "九江市", "上饶市", "鹰潭市"},//江西省11个
                {"枣庄市", "济南市", "德州市", "济宁市", "临沂市", "青岛市", "泰安市", "威海市", "淄博市", "菏泽市", "烟台市", "莱芜市", "滨州市", "东营市", "聊城市", "日照市", "潍坊市"},//山东省17个
                {"郑州市", "鹤壁市", "开封市", "濮阳市", "三门峡市", "新乡市", "驻马店市", "洛阳市", "商丘市", "济源市", "南阳市", "周口市", "信阳市", "焦作市", "漯河市", "平顶山市", "许昌市", "安阳市"},//河南省18个
                {"武汉市", "荆门市", "黄石市", "恩施土家族苗族自治州", "荆州市", "十堰市", "随州市", "襄阳市", "咸宁市", "仙桃市", "黄冈市", "潜江市", "天门市", "宜昌市", "神农架林区", "孝感市", "鄂州市"},//湖北省17个
                {"岳阳市", "郴州市", "衡阳市", "娄底市", "邵阳市", "永州市", "张家界市", "湘西土家族苗族自治州", "常德市", "怀化市", "湘潭市", "益阳市", "株洲市", "长沙市"},//湖南省14个
                {"广州市", "东莞市", "河源市", "江门市", "茂名市", "韶关市", "云浮市", "珠海市", "佛山市", "深圳市", "梅州市", "潮州市", "惠州市", "揭阳市", "清远市", "汕尾市", "阳江市", "肇庆市", "中山市", "湛江市", "汕头市"},//广东省21个
                {"五指山市", "文昌市", "东方市", "屯昌县", "临高县", "乐东黎族自治县", "保亭黎族苗族自治县", "定安县", "陵水黎族自治县", "儋州市", "白沙黎族自治县", "三亚市", "琼海市", "万宁市", "澄迈县", "昌江黎族自治县", "琼中黎族苗族自治县", "海口市", "三沙市"},//海南省19个

                {"阿坝藏族羌族自治州", "甘孜藏族自治州", "内江市", "自贡市", "广安市", "遂宁市", "巴中市", "德阳市", "乐山市", "凉山彝族自治州", "眉山市", "攀枝花市", "雅安市", "资阳市", "泸州市", "成都市", "达州市", "广元市", "绵阳市", "南充市", "宜宾市"},//四川省21个
                {"贵阳市", "六盘水市", "铜仁市", "黔南布依族苗族自治州", "遵义市", "安顺市", "毕节市", "黔西南布依族苗族自治州", "黔东南苗族侗族自治州"},//贵州省9个
                {"昆明市", "保山市", "楚雄彝族自治州", "大理白族自治州", "德宏傣族景颇族自治州", "迪庆藏族自治州", "红河哈尼族彝族自治州", "临沧市", "丽江市", "怒江傈僳族自治州", "普洱市", "曲靖市", "文山壮族苗族自治州", "西双版纳傣族自治州", "玉溪市", "昭通市"},//云南省16个
                {"汉中市", "渭南市", "延安市", "榆林市", "咸阳市", "西安市", "宝鸡市", "商洛市", "铜川市", "安康市"},//陕西省10个
                {"兰州市", "嘉峪关市", "金昌市", "临夏回族自治州", "平凉市", "武威市", "定西市", "张掖市", "白银市", "酒泉市", "陇南市", "天水市", "庆阳市", "甘南藏族自治州"},//甘肃省14个
                {"海西蒙古族藏族自治州", "果洛藏族自治州", "海北藏族自治州", "玉树藏族自治州", "黄南藏族自治州", "西宁市", "海东市", "海南藏族自治州"},//青海省8个
                {"屏东县", "宜兰县", "台东县", "台南县", "高雄县", "高雄市", "云林县", "苗粟县", "台中县", "花莲县", "南投县", "桃园县", "新竹县", "台南市", "澎湖县", "基隆市", "台北市", "台中市", "嘉义县", "彰化县"},//台湾省20个
                {"包头市", "呼伦贝尔市", "巴彦淖尔市", "赤峰市", "通辽市", "乌海市", "兴安盟", "锡林郭勒盟", "呼和浩特市", "鄂尔多斯市", "乌兰察布市", "阿拉善盟"},//内蒙古自治区12个
                {"南宁市", "崇左市", "桂林市", "贺州市", "钦州市", "梧州市", "百色市", "来宾市", "河池市", "北海市", "防城港市", "玉林市", "贵港市", "柳州市"},//广西壮族自治区14个
                {"阿里地区", "昌都市", "林芝市", "那曲地区", "日喀则市", "山南市", "拉萨市"},//西藏自治区7个

                {"中卫市", "石嘴山市", "吴忠市", "固原市", "银川市"},//宁夏回族自治区5个
                {"乌鲁木齐市", "阿克苏地区", "阿勒泰地区", "巴音郭楞蒙古自治州", "博尔塔拉蒙古自治州", "昌吉回族自治州", "哈密市", "和田地区", "克拉玛依市", "克孜勒苏柯尔克孜自治州", "喀什地区", "石河子市", "吐鲁番市", "塔城地区", "伊犁哈萨克自治州", "阿拉尔市", "图木舒克市", "五家渠市", "北屯市", "铁门关市", "可克达拉市", "双河市"},//新疆维吾尔自治区22个
                {"东区", "九龙城区", "观塘区", "湾仔区", "黄大仙区", "油尖旺区", "沙田区", "离岛区", "葵青区", "北区", "屯门区", "西贡区", "大埔区", "荃湾区", "元朗区", "中西区", "南区", "深水埗区"},//香港特别行政区18个
                {"氹仔", "澳门半岛", "路氹城", "路环"},//澳门特别行政区4个
        };
        IsolationInfoVO isolationInfoVO = new IsolationInfoVO();
        for (int i = 0; i < 100; i++) {
            isolationInfoVO.setuId(String.valueOf((10000 + i)));
            isolationInfoVO.setName("TestUser" + String.valueOf(i));
            isolationInfoVO.setType(Math.abs(new Random().nextInt(4)));
            isolationInfoVO.setPhone(String.valueOf(19805112444L + i));
            isolationInfoVO.setHotelId("1");
            isolationInfoVO.setRoomType("146389099501");
            Long a=24 * 60 * 60 * 1000L;
            if(i<30){
                isolationInfoVO.setCheckInTime(new Date(System.currentTimeMillis()-(30-i+2)*a));
                isolationInfoVO.setCheckOutTime(new Date(System.currentTimeMillis()-(30-i+2)*a+14*a));
            }else{
                int anInt = Math.abs(new Random().nextInt(30));
                isolationInfoVO.setCheckInTime(new Date(System.currentTimeMillis()-anInt*a));
                isolationInfoVO.setCheckOutTime(new Date(System.currentTimeMillis()-anInt*a+14*a));
            }

            isolationInfoVO.setRoomId("10000001");
            isolationInfoVO.setRoomName("503");
            int nextInt = Math.abs(new Random().nextInt(34));
            isolationInfoVO.setProvince(province[nextInt]);
            isolationInfoVO.setCity(city[nextInt][0]);
            isolationInfoVO.setStatus(Math.abs(new Random().nextInt(3)));
            isolationInfoVO.setWay(Math.abs(new Random().nextInt(2)+1));
            this.add(isolationInfoVO);
        }
        return null;
    }

}
