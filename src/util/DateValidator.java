/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.validation.base.ValidatorBase;
import java.util.Date;

/**
 *
 * @author Meedoch
 */
public class DateValidator extends ValidatorBase{
    private Date minDate = new Date();
    private Date maxDate= null;
    @Override
    protected void eval() {
        if (maxDate==null){
            JFXDatePicker control = (JFXDatePicker) srcControl.get();
            System.out.println(control);
        }
    }
    
}
