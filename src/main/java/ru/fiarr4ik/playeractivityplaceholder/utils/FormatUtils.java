package ru.fiarr4ik.playeractivityplaceholder.utils;

    public final class FormatUtils {

        private FormatUtils() {

        }

        public static String formatTime(long ticks) {
            long totalSeconds = ticks / 20;
            long days = totalSeconds / 86400;
            long hours = (totalSeconds % 86400) / 3600;
            long minutes = (totalSeconds % 3600) / 60;
            long seconds = totalSeconds % 60;

            return String.format("%d дней, %02d:%02d:%02d", days, hours, minutes, seconds);
        }
    }
