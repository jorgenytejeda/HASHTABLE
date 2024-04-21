import java.util.LinkedList;
import java.util.Scanner;

    
//En esta clase vamos a definir toda la tabla.
public class TablaHash {

   //definimos una capacidad para la tabla y su umbral de factor de carga.
    private static final int DEFAULT_CAPACITY = 10;
    private static final double FACTOR_CARGA_THRESHOLD = 0.7;
    

    private LinkedList<Entry>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    
    //definimos la tabla con una linkedlist eh agregado un ejemplo de phonebook para su implementacion 
    public TablaHash() {
        table = new LinkedList[DEFAULT_CAPACITY];
        size = 0;
        put("Margarita", "124578963");
        put("Carmen", "189624573");
        put("Esperanza", "578912463");
        put("Ana", "457812963");
        put("Jonh", "196324578");
        put("Fernando", "128963457");
        put("Chuy", "178924563");
        put("Job", "457896312");
        put("Cabelis", "896124573");
        put("Emily", "172458963");

    }
    

    //definimos la llave tipo string
    private int hash(String llave) {
        return Math.abs(llave.hashCode() % table.length);
    }

    @SuppressWarnings("unchecked") //para poder suprimir las advertencias relacionadas con casting inseguras
    private void resize() {
        int newCapacity = table.length * 2;
        LinkedList<Entry>[] newTable = new LinkedList[newCapacity];

//calculando el tamano de la tabla para evitar factor de carga bajo
        for (LinkedList<Entry> list : table) {
            if (list != null) {
                for (Entry entry : list) {
                    int hash = hash(entry.llave);
                    if (newTable[hash] == null) {
                        newTable[hash] = new LinkedList<>();
                    }
                    newTable[hash].add(entry);
                }
            }
        }

        table = newTable;
    }

    public void put(String llave, String value) {
        if ((double) size / table.length >= FACTOR_CARGA_THRESHOLD) {
            resize();
        }

        int hash = hash(llave);
        if (table[hash] == null) {
            table[hash] = new LinkedList<>();
        }

        for (Entry entry : table[hash]) {
            if (entry.llave.equals(llave)) {
                entry.value = value;
                return;
            }
        }

        table[hash].add(new Entry(llave, value));
        size++;
    }
//obtener valores de la tabla asociados a una llave especifica.
    public String get(String llave) {
        int hash = hash(llave);
        if (table[hash] != null) {
            for (Entry entry : table[hash]) {
                if (entry.llave.equals(llave)) {
                    return entry.value;
                }
            }
        }
        return null;
    }
    //opcion "eleminar" de la tabla.
    public void remove(String llave) {
        int hash = hash(llave);
        if (table[hash] != null) {
            for (Entry entry : table[hash]) {
                if (entry.llave.equals(llave)) {
                    table[hash].remove(entry);
                    size--;
                    return;
                }
            }
        }
    }

    public int size() {
        return size;
    }

    private static class Entry {
        private String llave;
        private String value;

        public Entry(String llave, String value) {
            this.llave = llave;
            this.value = value;
        }
    }

    
    public static void main(String[] args) {
        TablaHash phonebook = new TablaHash();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        //vamos a leer las olpciones como string y luego convertirla en enteros para comparamos con las opciones.
        while (!salir) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Buscar contacto");
            System.out.println("2. Eliminar contacto");
            System.out.println("3. Salir");

            String optionStr = scanner.nextLine();

            if (!optionStr.matches("\\d")) {
                System.out.println("Opción inválida. Intente de nuevo.");
                continue;
            }

            int option = Integer.parseInt(optionStr);
            //guardamos en varable el dato ingresado, lo comparamos con la lista y lo devolvemos en out.
            if (option == 1) {
                System.out.println("Ingrese el nombre del contacto a buscar:");
                String nameToSearch = scanner.nextLine();
                String phoneNumberFound = phonebook.get(nameToSearch);
                if (phoneNumberFound != null) {
                    System.out.println("Número de teléfono de " + nameToSearch + ": " + phoneNumberFound);
                } else {
                    System.out.println("Contacto no encontrado.");
                }
            } else if (option == 2) {
                System.out.println("Ingrese el nombre del contacto a eliminar:");
                String nameToRemove = scanner.nextLine();
                phonebook.remove(nameToRemove);
                System.out.println("Contacto eliminado.");
            } else if (option == 3) {
                salir = true;
            } else {
                System.out.println("Opción inválida. Intente de nuevo.");
            }
        }

        scanner.close();
    }
}
        
    

