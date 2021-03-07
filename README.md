# MVP Setup Plugin

<!--  [![Build](https://github.com/vsay01/Android-Studio-MVP-Template-Google-Architecture-Plugin/workflows/Build/badge.svg)][gh:build] -->

<!-- Plugin description -->

Starting with Android Studio 4.1, Google ended its support for custom FreeMarker templates. :( 
Now you can’t just write your FTL files and put them in a template folder of Android Studio

So here is an Android Studio Plugin for setting Model-View-Presenter (MVP) template; the template is inspired by google samples/ android architecture

**Important:** The main goal of this template is to speed up the development process using android mvp template. It also shows how to create a set of files using template files in Android Studio. This template was successfully tested with Android 4.1.2.

> **For Pre Android Studio 4.1**: You can install this template from https://github.com/vsay01/Android-Studio-MVP-Template-Google-Architecture

<!-- Plugin description end -->

## How to use

#### 1. Create Base classes

The main idea of the base classes is to have common methods that share across the activities, fragments, contracts and presenters, written down in one place.

For instance: In our MVP template, all views will need to set the presenter so wrote a  method, setPresenter(), in the BasePresenter so all the presenters class inherite from BasePresenter need to implement the method.

Here are the needed bases classes:

- BaseActivity

**Java**
```
package com.template.test.testmvptemplate; // your application package

import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
  // TODO: add any relevance methods
}
```

**Kotlin**
```
package com.template.test.testmvptemplate

import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

}
```

- BaseFragment

**Java**
```
package com.template.test.testmvptemplate; // your application package

import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
  // TODO: add any relevance methods
}
```

**Kotlin**
```
package com.template.test.testmvptemplate

import android.support.v4.app.Fragment

open class BaseFragment : Fragment() {

}
```

- BaseContract

**Java**
```
package com.template.test.testmvptemplate; // your application package

public interface BaseContract {

  interface View<T extends Presenter> {
    void setPresenter(T presenter);
  }

  interface Presenter {
    void start();
    void stop();
  }
}
```

**Kotlin**
```
package com.template.test.testmvptemplate

interface BaseContract {
	interface View<T : Presenter?> {
		fun setPresenter(presenter: T)
	}

	interface Presenter {
		fun start()
		fun stop()
	}
}
```

- BasePresenter

**Java**
```
package com.template.test.testmvptemplate; // your application package

import android.content.Context;
import android.support.annotation.NonNull;

public class BasePresenter {
  protected Context mContext;

  public void subscribe(@NonNull Context context) {
    this.mContext = context;

  public boolean isSubscribed() {
    return mContext != null;
  }
}
```

**Kotlin**
```
package com.template.test.testmvptemplate

import android.content.Context

open class BasePresenter {

	private var mContext: Context? = null

	fun subscribe(context: Context) {
		mContext = context
	}

	val isSubscribed: Boolean
		get() = mContext != null
}
```
#### 2. Generate MVP template
<img src="/screenshots/screenshot_1.png" width="260">

#### 3. Resolve imports and style of the added activity in manifest
Import classes as needed, and currently it will add activity automatically to manifest with style. You need to make sure the style got resolved.

## Related links

- [Android-Studio-MVP-Template-Google-Architecture for pre Android Studio 4.1][Android-Studio-MVP-Template-Google-Architecture github]
- [Android Studio MVP Template Google Architecture for pre Android Studio 4.1][Android-Studio-MVP-Template-Google-Architecture article]

[Android-Studio-MVP-Template-Google-Architecture github]: https://github.com/vsay01/Android-Studio-MVP-Template-Google-Architecture
[Android-Studio-MVP-Template-Google-Architecture article]: https://proandroiddev.com/android-studio-mvp-template-google-architecture-29c93a940341
[gh:build]: https://github.com/vsay01/Android-Studio-MVP-Template-Google-Architecture-Plugin/actions?query=workflow%3ABuild
