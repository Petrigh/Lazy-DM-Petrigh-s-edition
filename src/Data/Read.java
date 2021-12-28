package Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Read {
    private boolean isEmptyString(String string) {
        return string == null || string.isEmpty();
    }
    private boolean validate(String text, StringBuilder error) {

        if(isEmptyString(text)) {
            fillString(error,1);
            return true;
        }
        return false;
    }
    private boolean checkTel(String text,StringBuilder error) { 	//Checkea que solo contenga numeros
        if(isEmptyString(text)) {
            fillString(error,1);
            return true;
        }
        char[] s = text.toCharArray();
        for (char c: s){
            if (Character.isLetter(c)){
                fillString(error,4);
                return true;
            }
        }
        return false;
    }

    private boolean checkForSpecialChar(String text, StringBuilder error) {

        /*
            Esto funciona solo si el '-' esta detras del numero
            EJ: -3, pero lanza una excepcion si intentamos hacer '3-'
            **
            Estoy un poco confundido de porque hiciste una clase solo con
            metodos estaticos, pero ¿Quien soy para juzgar?

            El codigo fue robado de un proyecto que hice antes, asi que no lo cuestione, ahi lo arregle
        */


        Pattern regex = Pattern.compile("[$&+,:;=?@#|'<>.^*()%!]");

        Matcher matcher = regex.matcher(text);

        if(matcher.find()) {
            //error 4 porque no son numeros.
            fillString(error, 4);
            return true;
        }

        return false;
    }


    public boolean validate(String t1, String t2, StringBuilder error) {
        return validate(t1, error) || checkTel(t2, error) || checkForSpecialChar(t2, error);
    }
    private void fillString(StringBuilder zText, int i) {
        if(i==1) {
            zText.append ("Campo Vacio");
        }else if(i==4) {
            zText.append ("Iniciativa solo debe contener numeros");
        }else if(i==2){
            zText.append ("Jugador ya se encuentra guardado");
        }
    }
}
