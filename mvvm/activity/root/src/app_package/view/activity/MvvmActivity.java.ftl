package ${packageName}.view.activity;

import android.os.Bundle;

/**
 * @author: qflbai
 * @CreateDate: 
 * @Version: 1.0
 * @description:
 */
public class ${MvvmName}Activity extends AbsLifecycleActivity<${MvvmName}ViewModel> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.${layoutName});
    }
}
