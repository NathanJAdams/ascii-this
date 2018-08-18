package clean.transform;

import java.util.ArrayList;
import java.util.List;

public class Transformation {
    public static final Transformation NULL_TRANSFORMATION = new Transformation.Builder().build();

    private final int added;
    private final int removed;
    private final int changed;
    private final int moved;
    private final List<TransformCommand> commands;

    public Transformation(int added, int removed, int changed, int moved, List<TransformCommand> commands) {
        this.added = added;
        this.removed = removed;
        this.changed = changed;
        this.moved = moved;
        this.commands = commands;
    }

    public int getAdded() {
        return added;
    }

    public int getRemoved() {
        return removed;
    }

    public int getChanged() {
        return changed;
    }

    public int getMoved() {
        return moved;
    }

    public List<TransformCommand> getCommands() {
        return commands;
    }

    public static class Builder {
        private final List<TransformCommand> commands = new ArrayList<>();
        private int added;
        private int removed;
        private int changed;
        private int moved;

        public Builder add(int add) {
            this.added += add;
            return this;
        }

        public Builder remove(int remove) {
            this.removed += remove;
            return this;
        }

        public Builder change(int change) {
            this.changed += change;
            return this;
        }

        public Builder move(int move) {
            this.moved += move;
            return this;
        }

        public void command(TransformCommand command) {
            this.commands.add(command);
        }

        public Transformation build() {
            return new Transformation(added, removed, changed, moved, commands);
        }
    }
}
