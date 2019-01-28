# Paint - PathEffect

## [PathEffect](https://developer.android.com/reference/android/graphics/PathEffect)
![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1548579568831-bf7d832a-ddea-454c-8350-386b76af74a3.png#align=left&display=inline&height=249&linkTarget=_blank&name=image.png&originHeight=249&originWidth=880&size=30178&width=880)

![](https://cdn.nlark.com/yuque/__puml/f3ada39830efe6e2c38436dcd8286e73.svg#card=puml&code=%40startuml%0Ahide%20member%0A%0APathEffect%20%3C%7C--%20CornerPathEffect%0APathEffect%20%3C%7C--%20DiscretePathEffect%0APathEffect%20%3C%7C--%20DashPathEffect%0APathEffect%20%3C%7C--%20PathDashPathEffect%0APathEffect%20%3C%7C--%20SumPathEffect%0APathEffect%20%3C%7C--%20ComposePathEffect%0A%0A%40enduml)
PathEffect is the base class for objects in the Paint that affect the geometry of a drawing primitive before it is transformed by the canvas' matrix and drawn.

该系列类没有实例方法，只有构造方法，只需要构造后，传入 [`Paint.setPathEffect(PathEffect effect)`](https://developer.android.com/reference/android/graphics/Paint#setPathEffect(android.graphics.PathEffect)) 即可。<br /><br /><br />概览<br />![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1548665707764-5caa4d1e-7fe9-4ea5-bddf-9b7b087174f3.png#align=left&display=inline&height=504&linkTarget=_blank&name=image.png&originHeight=2017&originWidth=1080&size=1042010&width=270)<br /><br /><br />原图：<br />![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1548664856505-a139f90f-0de1-48c4-9717-b134cc61fa8b.png#align=left&display=inline&height=150&linkTarget=_blank&name=image.png&originHeight=300&originWidth=1080&size=45973&width=540)

## [CornerPathEffect](https://developer.android.com/reference/android/graphics/CornerPathEffect)

`CornerPathEffect(float radius)`

| Parameters |  |
| :--- | --- |
| **radius** | **float:** Amount to round sharp angles between line segments. |

Transforms geometries that are drawn (either `STROKE` or `FILL` styles) by replacing any sharp angles between line segments into rounded angles of the specified **radius**.

![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1548664821721-0bef4903-c4cb-4240-8fd1-39cc76d4d48d.png#align=left&display=inline&height=150&linkTarget=_blank&name=image.png&originHeight=300&originWidth=1080&size=43588&width=540)

## [DiscretePathEffect](https://developer.android.com/reference/android/graphics/DiscretePathEffect)

`DiscretePathEffect(float segmentLength, float deviation)`

| Parameters |  |
| :--- | --- |
| **segmentLength** | **float** |
| **deviation** | **float** |

Chop the path into lines of **segmentLength**deviation**.

segmentLength=1dp, deviation=10dp<br />![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1548664847652-9048d9d9-b1d2-4630-a182-6ed2a7ebf2c6.png#align=left&display=inline&height=150&linkTarget=_blank&name=image.png&originHeight=300&originWidth=1080&size=56415&width=540)

segmentLength=10dp, deviation=10dp<br />![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1548665357252-355f91c4-c206-41c4-92b9-7288f26eefca.png#align=left&display=inline&height=150&linkTarget=_blank&name=image.png&originHeight=300&originWidth=1080&size=46523&width=540)

## [DashPathEffect](https://developer.android.com/reference/android/graphics/DashPathEffect)

`DashPathEffect(float[] intervals, float phase)`

| Parameters |  |
| :--- | --- |
| **intervals** | **float:** array of ON and OFF distances |
| **phase** | **float:** offset into the intervals array<br />整体偏移, 动态设置 phase, 可以产生蚂蚁线效果 |

The **intervals** array must contain an even number of entries (>=2), with the even indices specifying the "on" intervals, and the odd indices specifying the "off" intervals.<br />**phase** is an offset into the intervals array (mod the sum of all of the intervals).<br />The **intervals** array controls the length of the dashes. The paint's **strokeWidth** controls the thickness of the dashes.<br />Note: this patheffect only affects drawing with the paint's style is set to `STROKE` or `FILL_AND_STROKE`. It is ignored if the drawing is done with style == `FILL`.

![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1548664840712-890d0f87-11ab-46d8-bc01-c6ba191a6499.png#align=left&display=inline&height=150&linkTarget=_blank&name=image.png&originHeight=300&originWidth=1080&size=38766&width=540)

## [PathDashPathEffect](https://developer.android.com/reference/android/graphics/PathDashPathEffect)
`PathDashPathEffect(Path shape, float advance, float phase, PathDashPathEffect.Style style)`

| Parameters |  |
| :--- | --- |
| **shape** | **Path:** The path to stamp along<br />shape 需要是闭合路径 |
| **advance** | **float:** spacing between each stamp of shape<br />shape 之间的间距, 忽略 shape 本身的大小 |
| **phase** | **float:** amount to offset before the first shape is stamped<br />整体偏移, 动态设置 phase, 可以产生蚂蚁线效果 |
| **style** | **PathDashPathEffect.Style:** how to transform the shape at each position as it is stamped<br />枚举取值: MORPH, ROTATE, TRANSLATE<br />MORPH: 方向跟路径一致, 在拐角处变形<br />ROTATE: 方向跟路径一致, 拐角处保持不变, 有断开效果<br />TRANSLATE: 保持shape方向不变, 不随路径变化<br />MORPH 效果最好 |

Dash the drawn path by stamping it with the specified **shape**.<br />This only applies to drawings when the paint's style is `STROKE` or `STROKE_AND_FILL`. If the paint's style is `FILL`, then this effect is ignored.<br />The paint's strokeWidth does not affect the results.

MORPH<br />![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1548664863639-b6587959-3e9d-4e50-a888-f85fd2dabf64.png#align=left&display=inline&height=150&linkTarget=_blank&name=image.png&originHeight=300&originWidth=1080&size=48222&width=540)

ROTATE<br />![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1548665180875-2eb31e84-0ab5-4170-81e5-d2b055f65e37.png#align=left&display=inline&height=150&linkTarget=_blank&name=image.png&originHeight=300&originWidth=1080&size=50052&width=540)

TRANSLATE<br />![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1548665231423-1a75ec1c-4acc-4c46-a443-42d323d49feb.png#align=left&display=inline&height=150&linkTarget=_blank&name=image.png&originHeight=300&originWidth=1080&size=19438&width=540)

## [SumPathEffect](https://developer.android.com/reference/android/graphics/SumPathEffect)

`SumPathEffect(PathEffect first, PathEffect second)`

Construct a PathEffect whose effect is to apply two effects, in sequence. (e.g. first(path) + second(path))<br />将两个 PathEffect 效果叠加，相当与绘制了2次。`first` 和 `second` 的顺序无影响。

DashPathEffect + CornerPathEffect<br />![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1548664900532-2e9fd4b6-7fc1-4848-a681-a969117be4ee.png#align=left&display=inline&height=150&linkTarget=_blank&name=image.png&originHeight=300&originWidth=1080&size=36808&width=540)

## [ComposePathEffect](https://developer.android.com/reference/android/graphics/ComposePathEffect)

`ComposePathEffect(PathEffect outerpe, PathEffect innerpe)`

Construct a PathEffect whose effect is to apply first the inner effect and the the outer pathEffect. (e.g. outer(inner(path)))<br />将两个 PathEffect 效果进行组合，先进行 `innerpe`，在这次的结果上在进行 `outerpe`。两次的顺序不同，结果也有差异。

CornerPathEffect(DashPathEffect())<br />![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1548664879647-bbd915aa-ac5c-4b69-9e3c-b75c718975c1.png#align=left&display=inline&height=150&linkTarget=_blank&name=image.png&originHeight=300&originWidth=1080&size=36885&width=540)

DashPathEffect(CornerPathEffect())<br />![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1548665104086-5734d060-769c-452a-9e7e-8dcb0bad5068.png#align=left&display=inline&height=150&linkTarget=_blank&name=image.png&originHeight=300&originWidth=1080&size=39044&width=540)


## 源码
[https://github.com/wenmin92/AndroidLearn/tree/master/app/src/main/java/cc/wenmin92/androidlearn/graphics/paint/path_effect](https://github.com/wenmin92/AndroidLearn/tree/master/app/src/main/java/cc/wenmin92/androidlearn/graphics/paint/path_effect)

