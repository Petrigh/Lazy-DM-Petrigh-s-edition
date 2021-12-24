package Data;


public class Read {

    public static boolean validate(String text,StringBuilder error) {
        if(text.isBlank()) {
            fillString(error,1);
            return true;
        }
        return false;
    }
    private static boolean checkTel(String text,StringBuilder error) { 	//Checkea que solo contenga numeros
        if(text.isBlank()) {
            fillString(error,1);
            return true;
        }
        char[] s = text.toCharArray();
        for (char c: s){
            if (!Character.isDigit(c)){
                fillString(error,4);
                return true;
            }
        }
        return false;
    }
    public static boolean validate(String t1,String t2,StringBuilder error) {
        if(validate(t1,error)||checkTel(t2,error)) return true;
        return false;
    }
    static void fillString(StringBuilder zText,int i) {
        if(i==1) {
            zText.append ("Campo Vacio");
        }else if(i==4) {
            zText.append ("Iniciativa solo debe contener numeros");
        }
    }
}
