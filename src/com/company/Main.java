package com.company;
import java.io.*;

class Link
{
    private String iData; // Данные
    public Link next; // Следующий элемент списка

    public Link(String it)
    { iData= it; }

    public String getKey()
    { return iData; }

    public void displayLink() // Вывод содержимого элемента
    { System.out.print(iData + " "); }
}

class SortedList
{
    private Link first; // Ссылка на первый элемент списка

    public void SortedList() // Конструктор
    { first = null; }

    public void insert(Link theLink) // Вставка в порядке сортировки
    {
        String key = theLink.getKey();
        Link previous = null; // Начиная с первого элемента
        Link current = first;
        // До конца списка
        while( current != null  )
        { // Или пока current <= key
            previous = current;
            current = current.next; // Переход к следующему элементу
        }
        if(previous==null) // В начале списка
            first = theLink; // first --> новый элемент
        else // Не в начале
            previous.next = theLink; // prev --> новый элемент
        theLink.next = current; // новый элемент --> current
    }

    public void delete(String key) // Удаление элемента
    { // (предполагается, что список не пуст)
        Link previous = null; // Начиная с первого элемента
        Link current = first;
        // До конца списка
        while( current != null && !key .equals( current.getKey() ))
        { // или пока key != current
            previous = current;
            current = current.next; // Переход к следующему элементу
        }
        // Отсоединение
        if(previous==null) // Если первый элемент,
            first = first.next; // изменить first
        else // В противном случае
            previous.next = current.next; // удалить текущий элемент
    } // end delete()


    public Link find(String key) // Поиск элемента с заданным ключом
    {
        Link current = first; // Начиная от начала списка
        // До конца списка
        while(current != null )
        { // или пока ключ не превысит current,
            if(current.getKey() .equals(key)) // Искомый элемент?
                return current; // Совпадение обнаружено
            current = current.next; // Переход к следующему элементу
        }
        return null; // Элемент не найден
    }

    public void displayList()
    {
        System.out.print("List (first-->last): ");
        Link current = first; // От начала списка
        while(current != null) // Перемещение до конца списка
        {
            current.displayLink(); // Вывод данных
            current = current.next; // Переход к следующему элементу
        }
        System.out.println("");
    }
}

class HashTable
{
    private SortedList[] hashArray; // Массив списков
    private static int arraySize;

    public HashTable(int size) // Конструктор
    {
        arraySize = size;
        hashArray = new SortedList[arraySize]; // Создание массива
        for(int j=0; j<arraySize; j++) // Заполнение массива
            hashArray[j] = new SortedList(); // списками
    }

    public void displayTable()
    {
        for(int j=0; j<arraySize; j++) // Для каждой ячейки
        {
            System.out.print(j + ". "); // Вывод номера ячейки
            hashArray[j].displayList(); // Вывод списка
        }
    }

    public static int hashFunc(String key)
    {
        int hashVal = 0;
        for(int j=0; j<key.length(); j++) // Слева направо
        {
            int letter = key.charAt(j) - 96; // Получение кода символа
            hashVal = (hashVal * 27 + letter) % arraySize; // Оператор %
        }
        return hashVal; // Без оператора %
    }

    public void insert(Link theLink) // Вставка элемента
    {
        String key = theLink.getKey();
        int hashVal = hashFunc(key); // Хеширование ключа
        hashArray[hashVal].insert(theLink); // Вставка в позиции hashVal
    }

    public void delete(String key) // Удаление элемента
    {
        int hashVal = hashFunc(key); // Хеширование ключа
        hashArray[hashVal].delete(key); // Удаление
    }

    public Link find(String key) // Поиск элемента
    {
        int hashVal = hashFunc(key); // Хеширование ключа
        Link theLink = hashArray[hashVal].find(key); // Поиск
        return theLink; // Метод возвращает найденный элемент
    }

}

class Main
{
    public static void main(String[] args) throws IOException
    {
        String aKey;
        Link aDataItem;
        int size, n, keysPerCell = 100;

        System.out.print("Enter size of hash table: ");
        size = getInt();


        HashTable theHashTable = new HashTable(size);


        while(true) // Взаимодействие с пользователем
        {
            System.out.print("Enter first letter of ");
            System.out.print("show, insert, delete, or find: ");
            char choice = getChar();
            switch(choice)
            {
                case 's':
                    theHashTable.displayTable();
                    break;
                case 'i':
                    System.out.print("Enter key value to insert: ");
                    aKey = getString();
                    aDataItem = new Link(aKey);
                    theHashTable.insert(aDataItem);
                    break;
                case 'd':
                    System.out.print("Enter key value to delete: ");
                    aKey = getString();
                    theHashTable.delete(aKey);
                    break;
                case 'f':
                    System.out.print("Enter key value to find: ");
                    aKey = getString();
                    aDataItem = theHashTable.find(aKey);
                    if(aDataItem != null)
                        System.out.println("Found " + aKey);
                    else
                        System.out.println("Could not find " + aKey);
                    break;
                default:
                    System.out.print("Invalid entry\n");
            }
        }
    }

    public static String getString() throws IOException
    {

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }

    public static char getChar() throws IOException
    {
        String s = getString();
        return s.charAt(0);
    }


    public static int getInt() throws IOException
    {
        String s = getString();
        return Integer.parseInt(s);
    }

}