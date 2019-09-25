package ${packageName}.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;


/**
 * @author: qflbai
 * @CreateDate:
 * @Version: 1.0
 * @description:
 */
public class ${MvvmName}ViewModel extends BaseViewModel<${MvvmName}Model> {
    public ${MvvmName}ViewModel(@NonNull Application application, ${MvvmName}Model model) {
        super(application, model);
    }
}
