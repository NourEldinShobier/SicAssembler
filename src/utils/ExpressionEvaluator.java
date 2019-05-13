package utils;

import core.LookupTables;
import core.validators.ErrorController;
import core.validators.SegmentsValidator;
import utils.errors.ErrorType;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ExpressionEvaluator {

    private static int ERROR = -1;

    public static int evaluate (String expression, int lineNumber) {

        /** debug */
        //LookupTables.setSymbolTable();

        /** segmentify expression*/
        String[] exps  =expression.split("(?<=\\+)|(?=\\+)|(?<=\\-)|(?=\\-)|(?<=\\*)|(?=\\*)|(?<=\\/)|(?=\\/)|(?<=\\))|(?=\\))|(?<=\\()|(?=\\()");

        /** search symtab for label */
        for(String label : exps)
        {
            String trimmed = label.trim();
            if(validateDec(trimmed)|| trimmed.equals(")") || trimmed.equals("(") || trimmed.equals("+") || trimmed.equals("-") || trimmed.equals("*") || trimmed.equals("/") || trimmed.equals("^") || trimmed.equals("%"))
                continue;
            if(!LookupTables.symbolTable.containsKey(trimmed)){
                ErrorController.getInstance().pushError(lineNumber, ErrorType.UndefinedSymbol);
                return ERROR;
            }
            else
            {
                /** replace labels by addresses*/
                expression = expression.replaceFirst(label, LookupTables.symbolTable.get(trimmed));
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
        return (int) result;
    }

    public static boolean validateDec(String str) {
        for (int i = 0; i < str.length(); i++)
            if (Character.digit(str.charAt(i), 10) == -1)
                return false;
        return true;
    }

}
