package utils;

import core.LookupTables;
import core.validators.ErrorController;
import core.validators.SegmentsValidator;
import utils.errors.ErrorType;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ExpressionEvaluator {

    private static String ERROR = null;

    public static String evaluate (String expression, int lineNumber) {

        /** debug */
        //LookupTables.setSymbolTable();

        /** segmentify expression*/
        //(?<=\*)|(?=\*)|(?<=\/)|(?=\/)|
        String[] exps  = expression.split("(?<=\\+)|(?=\\+)|(?<=\\-)|(?=\\-)|(?<=\\))|(?=\\))|(?<=\\()|(?=\\()");

        /** search symtab for label */
        for(String label : exps)
        {
            String trimmed = label.trim();
            if(validateDec(trimmed)|| trimmed.equals(")") || trimmed.equals("(") || trimmed.equals("+") || trimmed.equals("-") || trimmed.equals("^") || trimmed.equals("%"))
                continue;
            if(trimmed.equals("*")) {
                expression = expression.replaceFirst("\\*", "-3");
            }
            else if(!LookupTables.symbolTable.containsKey(trimmed)){
                ErrorController.getInstance().pushError(lineNumber, ErrorType.UndefinedSymbol);
                return ERROR;
            }
            else
            {
                /** replace labels by addresses*/
                expression = expression.replaceFirst(label, Integer.toString(Integer.parseInt(LookupTables.symbolTable.get(trimmed), 16)));
            }
        }

        /** evaluate the expression*/
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        Object result = null;
        try {
            result = engine.eval(expression);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        //System.out.println(result);

        int maxLength = 6;
        String res = Integer.toBinaryString((int)result);
        if (res.length() > maxLength*4)
            res = res.substring(res.length() - maxLength*4);

        return Integer.toHexString(Integer.parseInt(res,2));
        //return res;
    }

    public static boolean validateDec(String str) {
        for (int i = 0; i < str.length(); i++)
            if (Character.digit(str.charAt(i), 10) == -1)
                return false;
        return true;
    }

}
