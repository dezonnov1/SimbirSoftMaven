import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class ExampleTestNG {
//suite
//    group
//        test
//            class
//                method

    /**
     * Простейший пример
     */
    @Test
    public void testCalc(){
        Assert.assertEquals(2+2,4,"Two plus two is FOUR!");
    }

    /**
     * Проверка функции Math.pow()
     * @param base число, которое возводится в степень
     */
    @Test
    @Parameters({"num"})
    public void testMathPow2(double base){
        Assert.assertEquals(Math.pow(base,2),base*base);
    }

    /**
     * Проверка имени и пароля
     * @param name имя
     * @param pas пароль
     */
    @Test(description = "Check name and password")
    @Parameters({"name","pas"})
    public void testLogin(String name, String pas){
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(name, "VOVCHIK2005", "Login name incorrect");
        softAssert.assertEquals(pas, "CoolPass", "Login pas incorrect");
        softAssert.assertAll();
    }

    /**
     * Проверка персон по их имени и фамилии
     * @param personName имя персоны
     * @param personLastName фамилия персоны
     */
    @Test(dataProvider="persons")
    public void personsCheckFromDataProvider(String personName, String personLastName){
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(personName.matches("[PVS].+[a-z[A-Z]]"), true);
        softAssert.assertEquals(personLastName.matches("[PBK].+in"), true);
        softAssert.assertAll();
    }
    @DataProvider(name = "persons")
    public static Object[][] allPersons(){
        return new Object[][]{
                {"Petya","Pushin"},
                {"Vanya","Bashin"},
                {"Olga","Balka"},
                {"Semen","Kavkin"}
        };
    }
}
