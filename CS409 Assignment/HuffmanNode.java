package Couplers.InappropriateIntimacy;
// from https://rosettacode.org/wiki/Huffman_coding#Java

class HuffmanNode extends HuffmanTree {
    public final HuffmanTree left, right; // subtrees

    public HuffmanNode(HuffmanTree l, HuffmanTree r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}

