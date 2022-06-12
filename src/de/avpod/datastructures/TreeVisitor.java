package de.avpod.datastructures;

public interface TreeVisitor<N extends BinaryNode> {
    void visit(N node);

    void onNextLevel();

    void onPrevLevel();
}
