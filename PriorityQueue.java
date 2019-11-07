import java.util.ArrayList;//да-да, очередь будет на базе списка

class PriorityQueue {
    private ArrayList<BinaryTree> data;//список очереди
    private int nElems;//кол-во элементов в очереди

    public PriorityQueue() {
        data = new ArrayList<BinaryTree>();
        nElems = 0;
    }

    public void insert(BinaryTree newTree) {//вставка
        if (nElems == 0)
            data.add(newTree);
        else {
            for (int i = 0; i < nElems; i++) {
                if (data.get(i).getFrequence() > newTree.getFrequence()) {//если частота вставляемого дерева меньше
                    data.add(i, newTree);//чем част. текущего, то cдвигаем все деревья на позициях справа на 1 ячейку
                    break;//затем ставим новое дерево на позицию текущего
                }
                if (i == nElems - 1)
                    data.add(newTree);
            }
        }
        nElems++;//увеличиваем кол-во элементов на 1
    }

    public BinaryTree remove() {//удаление из очереди
        BinaryTree tmp = data.get(0);//копируем удаляемый элемент
        data.remove(0);//собственно, удаляем
        nElems--;//уменьшаем кол-во элементов на 1
        return tmp;//возвращаем удаленный элемент(элемент с наименьшей частотой)
    }
}