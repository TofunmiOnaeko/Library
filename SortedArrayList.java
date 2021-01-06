import java.util.ArrayList;

public class SortedArrayList<E> extends ArrayList<E> {

    public SortedArrayList() {super();}

    public static void main(String[] args) { }

    /*
    The sortArray method takes a Sorted Array List and an element as parameters, it adds the element to the arraylist and then evaluates where it should sit in the already sorted array list,
    This method uses the insertion sort algorithm to place the element at the right point in the array list.
     */
    public static <E extends Comparable> ArrayList<E> sortArray(SortedArrayList<E> array, E element) {
        array.add(element);

        int loc;

        for(loc = 1; loc < array.size(); loc++) {

            E value = array.get(loc);
            int location;

            for(location = loc; location > 0; location--) {

            if(array.get(location - 1).compareTo(value) > 0) {

                E temp = array.get(location - 1);
                array.set(location - 1, value);
                array.set(location, temp);

            } else { break; }

            }

        }
        return array;
    }

}
