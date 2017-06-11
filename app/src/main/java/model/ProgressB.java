package model;

import android.widget.ProgressBar;
import android.widget.TextView;

public final class ProgressB {
    private final android.widget.ProgressBar progressBar;
    private final TextView title;
    private final TextView number;

    public ProgressB(android.widget.ProgressBar progressBar, TextView title, TextView number) {
        this.progressBar = progressBar;
        this.title = title;
        this.number = number;
    }

    public android.widget.ProgressBar getProgressBar() {
        return progressBar;
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getNumber() {
        return number;
    }
}