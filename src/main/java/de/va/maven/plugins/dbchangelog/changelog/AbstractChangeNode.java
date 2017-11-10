package de.va.maven.plugins.dbchangelog.changelog;

public abstract class AbstractChangeNode extends AbstractNode<Changeset> {

    private int seqnum;

    protected AbstractChangeNode(final NodeType nodeType) {
        super(nodeType);
        this.seqnum = seqnum;
    }

    public int getSeqnum() {
        return this.seqnum;
    }

    public void setSeqnum(final int seqnum) {
        this.seqnum = seqnum;
    }

    /*
     * Since we will stream changes directly from the database to the file system we do not anticipate this for being
     * needed.
     */
    @Override
    public int compareTo(final AbstractNode<Changeset> other) {
        throw new UnsupportedOperationException();
    }
}
