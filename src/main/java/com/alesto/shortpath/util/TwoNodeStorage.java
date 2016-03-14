package com.alesto.shortpath.util;

import com.alesto.shortpath.graph.node.AnchorNode;

import java.util.Deque;
import java.util.LinkedList;

public abstract class TwoNodeStorage {
    private static final Deque<AnchorNode> twoNodeStorage = new LinkedList<>();


    /**
     * Responds on the click on a node.
     * If the storage contains the node - this node would be removed.
     * If the storage doesn't contain the node - trying to add node to the storage.
     *
     * @param node
     */
    public static void processClick(AnchorNode node) {
        if (twoNodeStorage.contains(node)) {
            removeFromStorage(node);
        } else {
            addToStorage(node);
        }
    }
    public static AnchorNode[] getAndClear() {
        if(isFull()) {
            AnchorNode[] storage = new AnchorNode[]{twoNodeStorage.getFirst(), twoNodeStorage.getLast()};
            clear();
            return storage;
        } else {
            clear();
            //todo alert
            return null;
        }
    }

    /**
     * Clears the storage. yes, this implementation looks like a shit, but for some
     * reason it doesn't work other way. It throws RuntimeException, but no harm is done.
     * TODO - repair.
     */
    public static void clear() {
        removeFromStorage(twoNodeStorage.getFirst());
        removeFromStorage(twoNodeStorage.getFirst());
    }

    /**
     * As far we can't connect more than two node at once we should check our storage
     * Checks whether our storage already contains two nodes.
     *
     * @return
     */
    public static boolean isFull() {
        return twoNodeStorage.size() == 2;
    }

    /**
     * Adds node to the storage if and only if it contains less than two nodes.
     * If the storage is full, this method replaces last node in the storage with passed node.
     *
     * @param node
     */
    private static void addToStorage(AnchorNode node) {
        if(isFull()) {
            removeFromStorage(twoNodeStorage.getLast());
        }
        twoNodeStorage.add(node);
        node.setState(AnchorNode.State.LINKABLE);
    }

    /**
     * Removes passed node from the storage.
     *
     * @param node
     */
    private static void removeFromStorage(AnchorNode node) {
        if(twoNodeStorage.contains(node)) {
            twoNodeStorage.remove(node);
            node.setState(AnchorNode.State.DEFAULT);
        }
    }
}
