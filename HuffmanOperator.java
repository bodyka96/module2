public class HuffmanOperator {
    private final byte ENCODING_TABLE_SIZE = 127;//длина таблицы
    private HuffmanTree mainHuffmanTree;//дерево Хаффмана (используется только для сжатия)
    private String myString;//исходное сообщение
    private int[] freqArray;//частотаная таблица
    private String[] encodingArray;//кодировочная таблица
    private double ratio;//коэффициент сжатия


    public HuffmanOperator(HuffmanTree MainHuffmanTree) {//for compress
        this.mainHuffmanTree = MainHuffmanTree;

        myString = mainHuffmanTree.getOriginalString();

        encodingArray = mainHuffmanTree.getEncodingArray();

        freqArray = mainHuffmanTree.getFrequenceArray();
    }

    public HuffmanOperator() {}//for extract;

    //---------------------------------------compression-----------------------------------------------------------
    private String getCompressedString() {
        String compressed = "";
        String intermidiate = "";//промежуточная строка(без добавочных нулей)
        //System.out.println("=============================Compression=======================");
        //displayEncodingArray();
        for (int i = 0; i < myString.length(); i++) {
            intermidiate += encodingArray[myString.charAt(i)];
        }
        //Мы не можем писать бит в файл. Поэтому нужно сделать длину сообщения кратной 8=>
        //нужно добавить нули в конец(можно 1, нет разницы)
        byte counter = 0;//количество добавленных в конец нулей (байта в полне хватит: 0<=counter<8<127)
        for (int length = intermidiate.length(), delta = 8 - length % 8;
             counter < delta ; counter++) {//delta - количество добавленных нулей
            intermidiate += "0";
        }

        //склеить кол-во добавочных нулей в бинарном предаствлении и промежуточную строку
        compressed = String.format("%8s", Integer.toBinaryString(counter & 0xff)).replace(" ", "0") + intermidiate;
        //System.out.println("===============================================================");
        return compressed;
    }



    public byte[] getBytedMsg() {//final compression
        StringBuilder compressedString = new StringBuilder(getCompressedString());
        byte[] compressedBytes = new byte[compressedString.length() / 8];
        for (int i = 0; i < compressedBytes.length; i++) {
            compressedBytes[i] = (byte) Integer.parseInt(compressedString.substring(i * 8, (i + 1) * 8), 2);
        }
        return compressedBytes;
    }
    //---------------------------------------end of compression----------------------------------------------------------------
    //------------------------------------------------------------extract-----------------------------------------------------
    public String extract(String compressed, String[] newEncodingArray) {
        String decompressed = "";
        String current = "";
        String delta = "";
        encodingArray = newEncodingArray;

        //displayEncodingArray();
        //получить кол-во вставленных нулей
        for (int i = 0; i < 8; i++)
            delta += compressed.charAt(i);
        int ADDED_ZEROES = Integer.parseInt(delta, 2);

        for (int i = 8, l = compressed.length() - ADDED_ZEROES; i < l; i++) {
            //i = 8, т.к. первым байтом у нас идет кол-во вставленных нулей
            current += compressed.charAt(i);
            for (int j = 0; j < ENCODING_TABLE_SIZE; j++) {
                if (current.equals(encodingArray[j])) {//если совпало
                    decompressed += (char)j;//то добавляем элемент
                    current = "";//и обнуляем текущую строку
                }
            }
        }

        return decompressed;
    }

    public String getEncodingTable() {
        String enc = "";
        for (int i = 0; i < encodingArray.length; i++) {
            if (freqArray[i] != 0)
                enc += (char)i + encodingArray[i] + '\n';
        }
        return enc;
    }

    public double getCompressionRatio() {
        return ratio;
    }


    public void displayEncodingArray() {//для отладки
        System.out.println("======================Encoding table====================");
        for (int i = 0; i < ENCODING_TABLE_SIZE; i++) {
            //if (freqArray[i] != 0) {
            System.out.print((char)i + " ");
            System.out.println(encodingArray[i]);
            //}
        }
        System.out.println("========================================================");
    }
}
