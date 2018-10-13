package com.repocleaner.clean.transform;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class Transformation {
    public static final Transformation NULL_TRANSFORMATION = new Transformation.Builder().build();

    private final String description;
    private final int added;
    private final int removed;
    private final int changed;
    private final int moved;
    private final List<TransformCommand> commands;

    @Getter
    public static class Builder {
        private final List<TransformCommand> commands = new ArrayList<>();
        private StringBuilder descriptionLines = new StringBuilder();
        private int added;
        private int removed;
        private int changed;
        private int moved;

        public Builder addDescriptionLine(String descriptionLine) {
            this.descriptionLines.append(descriptionLines);
            this.descriptionLines.append('\n');
            return this;
        }

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
            return new Transformation(descriptionLines.toString(), added, removed, changed, moved, commands);
        }
    }
}
