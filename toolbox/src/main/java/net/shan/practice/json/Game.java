package net.shan.practice.json;


public interface Game {
    public enum Chance{
        PC(1), MOBILE(2), ALL(3);
        private int value;
        private Chance(int value){
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}
