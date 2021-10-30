/*
 * Copyright 2021 Squircle IDE contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blacksquircle.ui.plugin.base

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.text.Editable
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.EditText
import com.blacksquircle.ui.language.base.Language
import com.blacksquircle.ui.language.base.model.ColorScheme

abstract class EditorPlugin(val pluginId: String) {

    protected val isAttached: Boolean
        get() = (editText as? EditorAccessor) != null

    protected val language: Language?
        get() = (editText as EditorAccessor).language
    protected val colorScheme: ColorScheme
        get() = (editText as EditorAccessor).colorScheme
    protected val lines: LinesCollection
        get() = (editText as EditorAccessor).lines
    protected val undoStack: UndoStack
        get() = (editText as EditorAccessor).undoStack
    protected val redoStack: UndoStack
        get() = (editText as EditorAccessor).redoStack

    protected var editText: EditText? = null

    open fun onAttached(editText: EditText) {
        this.editText = editText
        onColorSchemeChanged(colorScheme)
        onLanguageChanged(language)
    }

    open fun onDetached(editText: EditText) {
        this.editText = null
    }

    open fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) = Unit
    open fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) = Unit
    open fun beforeDraw(canvas: Canvas?) = Unit
    open fun afterDraw(canvas: Canvas?) = Unit

    open fun onColorSchemeChanged(colorScheme: ColorScheme) = Unit
    open fun onLanguageChanged(language: Language?) = Unit

    open fun onScrollChanged(horiz: Int, vert: Int, oldHoriz: Int, oldVert: Int) = Unit
    open fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) = Unit
    open fun onSelectionChanged(selStart: Int, selEnd: Int) = Unit
    open fun onTouchEvent(event: MotionEvent): Boolean = false
    open fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean = false
    open fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean = false

    open fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) = Unit
    open fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) = Unit
    open fun onTextReplaced(newStart: Int, newEnd: Int, newText: CharSequence) = Unit
    open fun afterTextChanged(text: Editable?) = Unit

    open fun addLine(lineNumber: Int, lineStart: Int, lineLength: Int) = Unit
    open fun removeLine(lineNumber: Int) = Unit
    open fun clearLines() = Unit

    open fun setTextContent(text: CharSequence) = Unit
    open fun setTextSize(size: Float) = Unit
    open fun setTypeface(tf: Typeface?) = Unit
    open fun setEmptyText() = Unit

    open fun showDropDown() = Unit

    protected fun requireContext(): Context {
        if (editText?.context == null) {
            throw IllegalStateException("EditorPlugin $this not attached to a context.")
        }
        return editText!!.context
    }
}