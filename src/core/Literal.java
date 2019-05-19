package core;

public class Literal {
    private static String hexValue = "";
    private static String MemoryValue = "";
    public static String literal ="";
    private static int length;

    public static String getHexValue() {
        return hexValue;
    }

    public static void calculateLiteralLength(){
        if(literal.startsWith("=x") || literal.startsWith("=X"))
            length = (literal.length() - 4);
        length = (6);
    }

    public static void literalToHex (){
        String hexaLiteral ="";
        String zeroes = "000000";
        StringBuilder builder = new StringBuilder();

        if(literal.startsWith("=c") || literal.startsWith("=C")) {
            literal = literal.substring(3, literal.length()-1);
            int len = literal.length();
            if(len < 3){
                builder.append(zeroes.substring(0, 5 - (2 * len)));
            }
            char[] characters = literal.toCharArray();
            for (char c : characters) {
                int i = (int) c;
                builder.append(Integer.toHexString(i).toUpperCase());
            }
            hexValue.concat(builder.toString());
            System.out.println(builder.toString());
        }

        else if(literal.startsWith("=w") || literal.startsWith("=W")){
            literal = literal.substring(3,literal.length()-1);
            int decimal =  (Integer.parseInt(literal));
            hexaLiteral = Integer.toHexString(decimal);
            int len = (hexaLiteral).length();
            if(len < 6){
                builder.append(zeroes.substring(0, 5 - len ));
                builder.append(hexaLiteral);
            }
            hexValue.concat(builder.toString());
        }

        hexValue.concat(literal.substring(3, literal.length()-1));
    }

    private static boolean isDuplicateLiteral(String literal){
        return false;

    }
}
