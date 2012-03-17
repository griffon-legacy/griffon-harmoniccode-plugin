/*
 * Copyright (c) 2010 griffon-harmoniccode - Andres Almiray. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of griffon-harmoniccode - Andres Almiray nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 'AS IS'
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * @author Andres Almiray
 */
class HarmoniccodeGriffonPlugin {
    // the plugin version
    String version = '0.2'
    // the version or versions of Griffon the plugin is designed for
    String griffonVersion = '0.9.5 > *'
    // the other plugins this plugin depends on
    Map dependsOn = [swing: '0.9.5', 'trident-builder': '0.8']
    // resources that are included in plugin packaging
    List pluginIncludes = []
    // the plugin license
    String license = 'BSD'
    // Toolkit compatibility. No value means compatible with all
    // Valid values are: swing, javafx, swt, pivot, gtk
    List toolkits = ['swing']
    // Platform compatibility. No value means compatible with all
    // Valid values are:
    // linux, linux64, windows, windows64, macosx, macosx64, solaris
    List platforms = []
    // URL where documentation can be found
    String documentation = ''
    // URL where source can be found
    String source = 'https://github.com/griffon/griffon-harmoniccode-plugin'

    List authors = [
        [
            name: 'Andres Almiray',
            email: 'aalmiray@yahoo.com'
        ]
    ]
    String title = "Gerrit Grunwald's Friday Fun components"
    // accepts Markdown syntax. See http://daringfireball.net/projects/markdown/ for details
    String description = '''
Delivers [@hansolo_][1]'s [Friday Fun components][2].

Usage
-----

The following nodes will become available on a View script upon installing this plugin

| *Node*           | *Type*                               |
| ---------------- | ------------------------------------ |
| animatedProgress | `eu.hansolo.custom.AnimatedProgress` |
| flipChar         | `eu.hansolo.custom.FlipChar`         |
| mbutton          | `eu.hansolo.custom.MButton`          |
| nixieNumber      | `eu.hansolo.custom.NixieNumber`      |
| note             | `eu.hansolo.custom.Note`             |
| rangeSlider      | `eu.hansolo.custom.RangeSlider`      |
| rollingCounter   | `eu.hansolo.custom.Counter`          |
| semaphore        | `eu.hansolo.custom.Semaphore`        |
| signalTower      | `eu.hansolo.custom.Design42`         |
| steelCheckBox    | `eu.hansolo.custom.SteelCheckBox`    |

The following Java2D paint classes are also available
 
 * `eu.hansolo.gradients.BiLinearGradientPaint`
 * `eu.hansolo.gradients.ConicalGradientPaint`
 * `eu.hansolo.gradients.ContourGradientPaint`
 
The following properties are also available

 * `COUNTER_THEME_BRIGHT`
 * `COUNTER_THEME_DARK`
 * `COLOR_DEF_RED`
 * `COLOR_DEF_ORANGE`
 * `COLOR_DEF_YELLOW`
 * `COLOR_DEF_GREEN`
 * `COLOR_DEF_BLUE`
 * `COLOR_DEF_GRAY`
 * `COLOR_DEF_CYAN`
 * `COLOR_DEF_MAGENTA`
 * `COLOR_DEF_RAITH`
 * `COLOR_DEF_GREEN_LCD`
 * `COLOR_DEF_JUG_GREEN`
 * `COLOR_DEF_WHITE`


[1]: http://twitter.com/hansolo_
[2]: http://harmoniccode.blogspot.com/
'''
}
