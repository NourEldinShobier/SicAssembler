package core.validators;

import java.util.ArrayList;
///instruction format

public class InstructionSet {

    private static InstructionFormat[] instFormat = {
            new InstructionFormat("rmo", "ac", true, false, false, true, false),

            new InstructionFormat("lda", "00", false, true, true, false, false),
            new InstructionFormat("ldb", "68", false, true, true, false, false),
            new InstructionFormat("ldch", "50", false, true, true, false, false),
            new InstructionFormat("ldf", "70", false, true, true, false, false),
            new InstructionFormat("ldl", "08", false, true, true, false, false),
            new InstructionFormat("lds", "6c", false, true, true, false, false),
            new InstructionFormat("ldt", "74", false, true, true, false, false),
            new InstructionFormat("ldx", "04", false, true, true, false, false),

            new InstructionFormat("sta", "0c", false, true, true, false, false),
            new InstructionFormat("stb", "78", false, true, true, false, false),
            new InstructionFormat("stch", "54", false, true, true, false, false),
            new InstructionFormat("stf", "80", false, true, true, false, false),
            new InstructionFormat("stl", "14", false, true, true, false, false),
            new InstructionFormat("sts", "7c", false, true, true, false, false),
            new InstructionFormat("stt", "84", false, true, true, false, false),
            new InstructionFormat("stx", "10", false, true, true, false, false),

            new InstructionFormat("add", "18", false, true, true, false, false),
            new InstructionFormat("sub", "1c", false, true, true, false, false),
            new InstructionFormat("addr", "90", true, false, false, true, false),
            new InstructionFormat("subr", "94", true, false, false, true, false),
            new InstructionFormat("comp", "28", false, true, true, false, false),
            new InstructionFormat("compr", "a0", true, false, false, true, false),


            new InstructionFormat("j", "3c", false, true, true, false, false),
            new InstructionFormat("jeq", "30", false, true, true, false, false),
            new InstructionFormat("jlt", "38", false, true, true, false, false),
            new InstructionFormat("jgt", "34", false, true, true, false, false),
            new InstructionFormat("tix", "2c", false, true, true, false, false),
            new InstructionFormat("tixr", "b8", true, false, true, false, false)
    };
    //29 elemnts

    public static InstructionFormat Search(String name) {
        for (int i = 0; i <= 28; i++) {
            if (name.toLowerCase().equals(instFormat[i].getName())) {
                return instFormat[i];
            }
        }
        return null;
    }


}
