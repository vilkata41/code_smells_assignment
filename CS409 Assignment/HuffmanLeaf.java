package Couplers.InappropriateIntimacy;
// from https://rosettacode.org/wiki/Huffman_coding#Java


class HuffmanLeaf extends HuffmanTree {
    public final char value; // the character this leaf represents

    public HuffmanLeaf(int freq, char val) {
        super(freq);
        value = val;
    }
}
