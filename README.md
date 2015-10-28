# TestDialogFragmentAnimation
必需要加外面这个框且在DialogFragment::onCreateView()中修改背景透明和在DialogFragment::onStart()中修改窗口为全屏，否则弹出对话框动画的效果是对话框只在自身大小的区域内活动且超出区域部分不显示，加了外面这个框且为全屏则其内容（实际对话框可见区域）动画活动时无被遮蔽情况

##Demo
![](https://github.com/wzhnsc/TestDialogFragmentAnimation/blob/master/gif/show.gif)
