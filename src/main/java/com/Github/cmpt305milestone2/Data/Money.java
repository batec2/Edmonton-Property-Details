/**
 * Class that converts BigDecimal to a money format
 * @author Crush Bate
 */
package com.Github.cmpt305milestone2.Data;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class Money {
    /**
     * Takes a BigDecimal and formats to the currency format of the local machine
     * @param bigDecimal
     * Takes BigDecimal Object
     * @return
     * String formatted BigDecimal in the currency type of the machine
     */
    public static String bigDecimalToMoney(BigDecimal bigDecimal){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return numberFormat.format(bigDecimal);
    }
}
