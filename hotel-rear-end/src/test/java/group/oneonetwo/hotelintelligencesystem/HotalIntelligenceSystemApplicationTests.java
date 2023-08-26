//package group.oneonetwo.hotelintelligencesystem;
//
//import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;
//import group.oneonetwo.hotelintelligencesystem.modules.hotel.service.IHotelService;
//import group.oneonetwo.hotelintelligencesystem.modules.menu.model.vo.MenuVO;
//import group.oneonetwo.hotelintelligencesystem.modules.menu.service.IMenuService;
//import group.oneonetwo.hotelintelligencesystem.modules.user.model.po.UserPO;
//import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
//import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtils;
//import group.oneonetwo.hotelintelligencesystem.tools.Page;
//import group.oneonetwo.hotelintelligencesystem.tools.WStringUtils;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//class HotalIntelligenceSystemApplicationTests {
//
//    @Autowired
//    IMenuService menuService;
//
//    @Autowired
//    IHotelService hotelService;
//
//    @Test
//    void contextLoads() {
//    }
//
//    @Test
//    void listConvertTest() {
//        List<UserPO> userPOS = new ArrayList<>();
//        List<UserVO> userVOS = new ArrayList<>();
//        UserPO userPO = new UserPO();
//        userPO.setId("123");
//        userPO.setDept("456");
//        userPOS.add(userPO);
//
//        List<UserVO> userVOS1 = ConvertUtils.transferList(userPOS, UserVO.class);
//        System.out.println(userVOS1);
//
//    }
//
//    @Test
//    void encode() {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String passHash = encoder.encode("admin");
//        System.out.println(passHash);
//    }
//
//    @Test
//    void getTree() {
//        List<MenuVO> menuTreeByDeptId = menuService.getMenuTreeByDeptId("1");
//        System.out.println(menuTreeByDeptId.toString());
//
//    }
//
//    @Test
//    void getPage() {
//        HotelVO hotelVO = new HotelVO();
//        Page<HotelVO> hotelVOPage = new Page<>();
//        hotelVOPage.setPage(1);
//        hotelVOPage.setSize(10);
//        hotelVO.setPage(hotelVOPage);
//        hotelService.getPage(hotelVO);
//    }
//
//
//
//}
