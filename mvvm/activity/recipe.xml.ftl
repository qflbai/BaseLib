<?xml version="1.0"?>
<recipe>

	<instantiate from="root/src/app_package/view/activity/MvvmActivity.java.ftl"
		to="${escapeXmlAttribute(srcOut)}/view/activity/${MvvmName}Activity.java" />

	<instantiate from="root/src/app_package/model/MvvmModel.java.ftl"
		to="${escapeXmlAttribute(srcOut)}/model/${MvvmName}Model.java" />

	<instantiate from="root/src/app_package/viewmodel/MvvmViewModel.java.ftl"
		to="${escapeXmlAttribute(srcOut)}/viewmodel/${MvvmName}ViewModel.java" />

	<instantiate from="root/res/layout/activity_mvvm_sample.xml.ftl"
		to="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />

    <merge from="root/AndroidManifest.xml.ftl"
           to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />

    
</recipe>