# Paint - Shader

:::info
本文链接 [https://www.yuque.com/wenmin92/android/paint-shader/edit](https://www.yuque.com/wenmin92/android/paint-shader/edit)
:::

## [Shader](https://developer.android.com/reference/android/graphics/Shader)
![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1550553931150-82d0a2a5-e99a-4ae3-9970-33e3a5a96f3e.png#align=left&display=inline&height=245&linkTarget=_blank&name=image.png&originHeight=245&originWidth=886&size=28897&width=886)

![](https://cdn.nlark.com/yuque/__puml/a2c569494c3b0b7acdd9bc88d80a1150.svg#card=puml&code=%40startuml%0Ahide%20empty%20members%0A%0AShader%20%3C%7C--%20BitmapShader%0AShader%20%3C%7C--%20ComposeShader%0AShader%20%3C%7C--%20LinearGradient%0AShader%20%3C%7C--%20SweepGradient%0AShader%20%3C%7C--%20RadialGradient%0A%0Aenum%20%22Shader.TileMode%22%20%7B%0A%09CLAMP%0A%09REPEAT%0A%09MIRROR%0A%7D%0A%0A%40enduml)
Shader is the based class for objects that return horizontal spans of colors during drawing. A subclass of Shader is installed in a Paint calling `paint.setShader(shader)`. After that any object (other than a bitmap) that is drawn with that paint will get its color(s) from the shader. <br />使用 Paint 的 `setShader(shader)` 方法为 Paint 安装，之后的绘制会使用这个 shader 获取颜色。

Shader是着色器的意思，为笔刷提供颜色。

本文将自定义一个简单的View，其中只绘制一个矩形，装载不同的 Shader 观察其效果。<br />默认未装载 Shader 时，使用蓝色填充这个矩形。

![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1550540740080-0619e3a1-fe48-4432-8faa-8c127139f27e.png#align=left&display=inline&height=522&linkTarget=_blank&name=image.png&originHeight=2088&originWidth=1080&size=72814&width=270)


## [BitmapShader](https://developer.android.com/reference/android/graphics/BitmapShader)
Shader used to draw a bitmap as a texture. The bitmap can be repeated or mirrored by setting the tiling mode.<br />`public BitmapShader (Bitmap bitmap, Shader.TileMode tileX, Shader.TileMode tileY)`<br />Call this to create a new shader that will draw with a bitmap.

| Parameters |  |
| :--- | --- |
| **bitmap** | **Bitmap:** The bitmap to use inside the shaderThis value must never be null. |
| **tileX** | **Shader.TileMode:** The tiling mode for x to draw the bitmap in.This value must never be null. |
| **tileY** | **Shader.TileMode:** The tiling mode for y to draw the bitmap in.This value must never be null. |

我们有一个图片：

![](https://cdn.nlark.com/yuque/0/2019/png/213107/1550543261090-743d4105-7136-4d66-b2df-7d13187c0bbc.png#align=left&display=inline&height=114&linkTarget=_blank&originHeight=114&originWidth=114&size=0&width=114)

注意，这张图片的四个角是有一定的圆弧的，也就是该Bitmap的四个角点处的像素都是透明的。

```java
Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
shader = new BitmapShader(bitmap, mTileMode, mTileMode);
```

当 TileMode 分别为 CLAMP，REPEAT，MIRROR 时，效果如下。注意：tileX 和 tileY 可以不同，这里演示的是相同的情况。

| CLAMP | REPEAT | MIRROR  |
| :---: | :---: | :---: |
| ![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1550541024555-f95b52f5-2b0f-4853-88a9-3ee77a71cf6d.png#align=left&display=inline&height=2088&linkTarget=_blank&name=image.png&originHeight=2088&originWidth=1080&size=90085&status=done&width=1080) | ![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1550541048160-6c5309a1-a12a-4400-bafb-062a348bd569.png#align=left&display=inline&height=2088&linkTarget=_blank&name=image.png&originHeight=2088&originWidth=1080&size=205004&status=done&width=1080) | ![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1550541041195-5f9087bb-ae31-4dd0-8319-335275afac14.png#align=left&display=inline&height=2088&linkTarget=_blank&name=image.png&originHeight=2088&originWidth=1080&size=284171&status=done&width=1080) |

可以看到，当 TileMode 为 CLAMP 时，由于所绘制的矩形区域比Bitmap大，Bitmap就用右侧边和下侧边的最外层的颜色填充了矩形区域。由于原Bitmap右下角的像素是透明的，所以绘制的矩形的右下角就用透明填充了。

不透明的示例：<br />![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1550542653336-9ca29fd7-e3b8-487d-acf2-d645515fe0cf.png#align=left&display=inline&height=522&linkTarget=_blank&name=image.png&originHeight=2088&originWidth=1080&size=256436&width=270)

使用的场景是找专门的图片做纹理效果。


## [LinearGradient](https://developer.android.com/reference/android/graphics/LinearGradient)
线性渐变。<br />渐变着色。有2个构造方法，其中一个是为简化只有2种颜色的情况。

`public LinearGradient (float x0, float y0, float x1, float y1, int[] colors, float[] positions, Shader.TileMode tile)`<br />`public LinearGradient (float x0, float y0, float x1, float y1, int color0, int color1, Shader.TileMode tile)`<br />Create a shader that draws a linear gradient along a line. 

| Parameters |  |
| :--- | --- |
| **x0** | **float:** The x-coordinate for the start of the gradient line |
| **y0** | **float:** The y-coordinate for the start of the gradient line |
| **x1** | **float:** The x-coordinate for the end of the gradient line |
| **y1** | **float:** The y-coordinate for the end of the gradient line |
| **colors** | **int:** The colors to be distributed along the gradient lineThis value must never be null. |
| **positions** | **float:** May be null. The relative positions [0..1] of each corresponding color in the colors array. If this is null, the the colors are distributed evenly along the gradient line.<br />不是必须要包含0.0f和1.0f，不包含时，两边颜色范围扩展。 |
| **tile** | **Shader.TileMode:** The Shader tiling modeThis value must never be null. |

构造方法很简单，提供线性渐变的起始和结束坐标，渐变的颜色和位置，以及平铺模式。<br />可以参考 PhotoShop 等专业绘图软件提供的渐变工具进行理解。也可以参考 [Online Gradient Generator](http://angrytools.com/gradient/)。

```java
shader = new LinearGradient(getWidth() / 4f, getHeight() / 4f, getWidth() / 4f * 3f, getHeight() / 4f * 3f, new int[]{Color.BLUE, Color.YELLOW, Color.GREEN}, null, mTileMode);
```

当 TileMode 分别为 CLAMP，REPEAT，MIRROR 时，效果如下。

| CLAMP | REPEAT | MIRROR |
| :---: | :---: | :---: |
| ![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1550544451705-5f8c74d3-5031-4910-842d-a6ee480de00c.png#align=left&display=inline&height=2088&linkTarget=_blank&name=image.png&originHeight=2088&originWidth=1080&size=207999&status=done&width=1080) | ![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1550544478112-4576d1d2-44b1-45ac-86c3-50e6d9c6c265.png#align=left&display=inline&height=2088&linkTarget=_blank&name=image.png&originHeight=2088&originWidth=1080&size=261549&status=done&width=1080) | ![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1550544472016-f78cdbbf-9614-4c9e-b948-04b0ed239ccb.png#align=left&display=inline&height=2088&linkTarget=_blank&name=image.png&originHeight=2088&originWidth=1080&size=257509&status=done&width=1080) |

图中的虚线方框表明渐变的范围，左上角和右下角分别为渐变的起点和终点。


## [RadialGradient](https://developer.android.com/reference/android/graphics/RadialGradient)
径向渐变。<br />跟 LinearGradient 类似，也有2个构造方法，很容易理解，其中一个是只有2个颜色时的便捷方法。

`public RadialGradient (float centerX, float centerY, float radius, int[] colors, float[] stops, Shader.TileMode tileMode)`<br />`public RadialGradient (float centerX, float centerY, float radius, int centerColor, int edgeColor, Shader.TileMode tileMode)`<br />Create a shader that draws a radial gradient given the center and radius.

| Parameters |  |
| :--- | --- |
| **centerX** | **float:** The x-coordinate of the center of the radius |
| **centerY** | **float:** The y-coordinate of the center of the radius |
| **radius** | **float:** Must be positive. The radius of the circle for this gradient. |
| **colors** | **int:** The colors to be distributed between the center and edge of the circleThis value must never be null. |
| **stops** | **float:** May be null. Valid values are between 0.0f and 1.0f. The relative position of each corresponding color in the colors array. If null, colors are distributed evenly between the center and edge of the circle.<br />不是必须要包含0.0f和1.0f，不包含时，两边颜色范围扩展。 |
| **tileMode** | **Shader.TileMode:** The Shader tiling modeThis value must never be null. |

同样的。构造方法很简单，提供径向渐变的中心坐标和半径，渐变的颜色和位置，以及平铺模式。<br />可以参考 PhotoShop 等专业绘图软件提供的渐变工具进行理解。也可以参考 [Online Gradient Generator](http://angrytools.com/gradient/)。

```java
shader = new RadialGradient(getWidth() / 2f, getHeight() / 2f, getWidth() / 3f, new int[]{Color.BLUE, Color.YELLOW, Color.GREEN}, null, mTileMode);
```

当 TileMode 分别为 CLAMP，REPEAT，MIRROR 时，效果如下。

| CLAMP | REPEAT | MIRROR |
| :---: | :---: | :---: |
| ![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1550546398216-80cd5583-32a0-472d-a12b-e43bbda2e8ee.png#align=left&display=inline&height=2088&linkTarget=_blank&name=image.png&originHeight=2088&originWidth=1080&size=227008&status=done&width=1080) | ![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1550546405253-95e62951-5ee1-4585-8ebd-f8f5dda1ffcd.png#align=left&display=inline&height=2088&linkTarget=_blank&name=image.png&originHeight=2088&originWidth=1080&size=586970&status=done&width=1080) | ![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1550546412716-f13de8b1-fc79-40fe-81f6-ea6586f8b243.png#align=left&display=inline&height=2088&linkTarget=_blank&name=image.png&originHeight=2088&originWidth=1080&size=579357&status=done&width=1080) |

图中的虚线圆表明渐变的范围，中心和边界分别为渐变的起点和终点。


## [SweepGradient](https://developer.android.com/reference/android/graphics/SweepGradient)
角度渐变。<br />跟 LinearGradient 类似，也有2个构造方法，很容易理解，其中一个是只有2个颜色时的便捷方法。

`public SweepGradient (float cx, float cy, int[] colors, float[] positions)`<br />`public SweepGradient (float cx, float cy, int color0,  int color1)`<br />A Shader that draws a sweep gradient around a center point.

| Parameters |  |
| :--- | --- |
| **cx** | **float:** The x-coordinate of the center |
| **cy** | **float:** The y-coordinate of the center |
| **colors** | **int:** The colors to be distributed between around the center. There must be at least 2 colors in the array.This value must never be null.<br />第一个颜色和最后一个颜色可以相同，这样效果比较和谐。 |
| **positions** | **float:** May be NULL. The relative position of each corresponding color in the colors array, beginning with 0 and ending with 1.0. If the values are not monotonic, the drawing may produce unexpected results. If positions is NULL, then the colors are automatically spaced evenly.This value may be null.<br />不是必须要包含0.0f和1.0f，不包含时，两边颜色范围扩展。 |

同样的。构造方法很简单，提供角度渐变的中心坐标和半径，渐变的颜色和位置，以及平铺模式。不能设置角度，固定为3点钟方向。可以通过旋转间接实现自定角度。这个 Shader 不支持 TileMode。<br />可以参考 PhotoShop 等专业绘图软件提供的渐变工具进行理解。

```java
shader = new SweepGradient(getWidth() / 2f, getHeight() / 2f, new int[]{Color.BLUE, Color.YELLOW, Color.GREEN}, null);
```

![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1550552094232-5a45b51b-cdf6-4144-b79f-0787233563a8.png#align=left&display=inline&height=522&linkTarget=_blank&name=image.png&originHeight=2088&originWidth=1080&size=333607&width=270)


## [ComposeShader](https://developer.android.com/reference/android/graphics/ComposeShader)
A subclass of shader that returns the composition of two other shaders, combined by an [Xfermode](https://developer.android.com/reference/android/graphics/Xfermode.html) subclass.<br />`public ComposeShader (Shader shaderA, Shader shaderB, Xfermode mode)`<br />`public ComposeShader (Shader shaderA, Shader shaderB, PorterDuff.Mode mode)`<br />Create a new compose shader, given shaders A, B, and a combining mode. When the mode is applied, it will be given the result from shader A as its "dst", and the result from shader B as its "src".

| Parameters |  |
| :--- | --- |
| **shaderA** | **Shader:** The colors from this shader are seen as the "dst" by the modeThis value must never be null. |
| **shaderB** | **Shader:** The colors from this shader are seen as the "src" by the modeThis value must never be null. |
| **mode** | **PorterDuff.Mode:** The PorterDuff mode that combines the colors from the two shaders.This value must never be null. |

该效果可以通过给 Paint 设置 `setXfermode` 绘制2次达到类似效果。

```java
Shader shader1 = new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.windmill), mTileMode, mTileMode);
Shader shader2 = new LinearGradient(0f, 0f, getWidth(), getHeight(), 0xFF86B850, 0xFFF5B751, mTileMode);
shader = new ComposeShader(shader1, shader2, PorterDuff.Mode.MULTIPLY);
```

![image.png](https://cdn.nlark.com/yuque/0/2019/png/213107/1550555058929-52e3b1bd-8a00-4ea6-9109-617c17b0a338.png#align=left&display=inline&height=522&linkTarget=_blank&name=image.png&originHeight=2088&originWidth=1080&size=862455&width=270)

## 参考资料
* 实验源码 [Github](https://github.com/wenmin92/AndroidLearn/tree/master/app/src/main/java/cc/wenmin92/androidlearn/graphics/paint/shader)
* [Android中Canvas绘图之Shader使用图文详解 - 孙群 - CSDN博客](https://blog.csdn.net/iispring/article/details/50500106)

