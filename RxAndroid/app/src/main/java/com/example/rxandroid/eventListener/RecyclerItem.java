package com.example.rxandroid.eventListener;

import android.graphics.drawable.Drawable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class RecyclerItem {
    Drawable image;
    String title;
}
