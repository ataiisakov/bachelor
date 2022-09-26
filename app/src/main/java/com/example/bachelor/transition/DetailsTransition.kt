package com.example.bachelor.transition

import android.content.Context
import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.util.AttributeSet
import com.example.bachelor.R

class DetailsTransition : TransitionSet {
    constructor()
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    init {
        ordering = ORDERING_TOGETHER
        duration = R.integer.short_duration.toLong()
        addTransition(ChangeBounds())
        addTransition(ChangeTransform())
    }
}

