package MultithreadDemo;

import lombok.Getter;

/**
 * @Description 枚举类可以作为数据库
 * @Author shawn
 * @create 2019/5/15 0015
 */
@Getter
public enum CountryEnum {
    ONE(1,"齐"),TWO(2,"楚"),THREE(3,"燕"),FOUR(4,"赵"),FIVE(5,"魏"),SIX(6,"韩");

    private Integer returnCode;
    private String returnMsg;

    CountryEnum(Integer returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public static CountryEnum forEachCountryEnum(int index){
        CountryEnum[] values = CountryEnum.values();
        for (CountryEnum countryEnum : values){
            if(index == countryEnum.getReturnCode()){
                return countryEnum;
            }
        }
        return null;
    }
}
