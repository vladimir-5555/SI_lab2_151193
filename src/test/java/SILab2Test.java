import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SILab2Test {

    private final SILab2 objekt =new SILab2();
    private List<Integer> rezultat(Integer... elements){
        return new ArrayList<>(Arrays.asList(elements));
    }
    private List<Time> listaT(Time... elements){
        return new ArrayList<>(Arrays.asList(elements));
    }
    public Time novoVreme(int chasovi, int minuti, int sekundi) {
        return new Time(chasovi, minuti, sekundi);
    }
    private List<Integer> resultat2 = new ArrayList<>();

    @Test
    void multipleCondition() {

        RuntimeException ex;
        //if (hr < 0 || hr > 24)
        //Tochno||Nevazhno
        ex = assertThrows(RuntimeException.class,
                () -> objekt.function(listaT(novoVreme(-2, 0, 0))));
        assertEquals(ex.getMessage(), "The hours are smaller than the minimum");
        //Netochno||Tochno
        ex = assertThrows(RuntimeException.class,
                () -> objekt.function(listaT(novoVreme(26, 0, 0))));
        assertEquals(ex.getMessage(), "The hours are grater than the maximum");
        //Netochno||Netochno
        assertEquals(rezultat(3600), objekt.function(listaT(novoVreme(1, 0, 0))));

        //if(min < 0 || min > 59)
        //Tochno||Nevazhno
        ex = assertThrows(RuntimeException.class,
                () -> objekt.function(listaT(novoVreme(0, -2, 0))));
        assertEquals(ex.getMessage(), "The minutes are not valid!");
        //Netochno||Tochno
        ex = assertThrows(RuntimeException.class,
                () -> objekt.function(listaT(novoVreme(0, 60, 0))));
        assertEquals(ex.getMessage(), "The minutes are not valid!");
        //Netochno||Netochno
        assertEquals(rezultat(60), objekt.function(listaT(novoVreme(0, 1, 0))));

        //if(sec >= 0 && sec <= 59)
        //Tochno&&Tochno
        assertEquals(rezultat(59), objekt.function(listaT(novoVreme(0, 0, 59))));
        //Netochno&&Nevazhno
        ex = assertThrows(RuntimeException.class,
                () -> objekt.function(listaT(novoVreme(0, 0, -1))));
        assertEquals(ex.getMessage(), "The seconds are not valid");
        //Tochno&&Netochno
        ex = assertThrows(RuntimeException.class,
                () -> objekt.function(listaT(novoVreme(0, 0, 60))));
        assertEquals(ex.getMessage(), "The seconds are not valid");

        //if (hr == 24 && min == 0 && sec == 0)
        //Tochno&&Netochno&&Nevazhno
        ex = assertThrows(RuntimeException.class, () -> objekt.function(listaT(novoVreme(24, 1, 0))));
        assertEquals(ex.getMessage(), "The time is greater than the maximum");
        //Tochno&&Tochno&&Netochno
        ex = assertThrows(RuntimeException.class, () -> objekt.function(listaT(novoVreme(24, 0, 1))));
        assertEquals(ex.getMessage(), "The time is greater than the maximum");
        //Tochno&&Tochno&&Tochno
        assertEquals(rezultat(86400), objekt.function(listaT(novoVreme(24, 0, 0))));
    }

    @Test
    public void everyBranch() {
        RuntimeException iskluchok = null;

        List<Time> lista1 = new ArrayList<>();
        resultat2 = objekt.function(lista1);
        assertTrue(resultat2.isEmpty());

        List<Time> lista2 = new ArrayList<>();
        lista2.add(novoVreme(-2,42,42));

        try{
            objekt.function(lista2);
        }
        catch (RuntimeException ex){
            iskluchok =ex;
        }
        assertEquals("The hours are smaller than the minimum", iskluchok.getMessage());

        List<Time> lista3 = new ArrayList<>();
        lista3.add(novoVreme(30,47,47));

        try{
            objekt.function(lista3);
        }
        catch (RuntimeException ex){
            iskluchok =ex;
        }
        assertEquals("The hours are grater than the maximum", iskluchok.getMessage());

        List<Time> lista4 = new ArrayList<>();
        lista4.add(novoVreme(20,-3,3));

        try{
            objekt.function(lista4);
        }
        catch (RuntimeException ex){
            iskluchok =ex;
        }
        assertEquals("The minutes are not valid!", iskluchok.getMessage());

        List<Time> lista5 = new ArrayList<>();
        lista5.add(novoVreme(21,1,-1));

        try{
            objekt.function(lista5);
        }
        catch (RuntimeException ex){
            iskluchok =ex;
        }
        assertEquals("The seconds are not valid", iskluchok.getMessage());

        List<Time> lista6 = new ArrayList<>();
        lista6.add(novoVreme(23,4,5));

        resultat2.add(83045);
        assertEquals(resultat2, objekt.function(lista6));

        List<Time> lista7 = new ArrayList<>();
        lista7.add(novoVreme(24,0,0));

        resultat2.remove(0);
        resultat2.add(86400);
        assertEquals(resultat2, objekt.function(lista7));

        List<Time> lista8 = new ArrayList<>();
        lista8.add(novoVreme(24,3,1));

        try{
            objekt.function(lista8);
        }catch (RuntimeException ex){
            iskluchok =ex;
        }
        assertEquals("The time is greater than the maximum", iskluchok.getMessage());
    }
}