package com.qa.testRunner;

import com.qa.configs.*;
import com.qa.utils.*;
import org.testng.annotations.*;

import java.util.*;

public class TestExecutor implements GlobalVariables {

    @Factory
    public Object[] testCasesToExecute() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ArrayList<Object> tests = new ArrayList<Object>();
        ExcelUtil excel = new ExcelUtil();
        excel.setExcelFile(DATA_FOLDER + WORKBOOK);
        for(String scenario : excel.getScenariosToRun(SCENARIO_SHEET_NAME, RUN_MODE_COLUMN, TEST_CASE_COLUMN)) {
            tests.add(createObject(scenario));
        }
        return tests.toArray();
    }

    public Object createObject(String test) {
        Object object = null;
        try {
            object = Class.forName(String.format(TEST_PACKAGE, test)).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }
}
