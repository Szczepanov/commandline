package commandline.domain;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import commandline.annotations.AllAvailableArguments;
import commandline.annotations.ArgumentsUntilDelimiter;
import commandline.annotations.LongSwitch;
import commandline.annotations.Multiple;
import commandline.annotations.Option;
import commandline.annotations.Required;
import commandline.annotations.ShortSwitch;
import commandline.annotations.SingleArgument;
import commandline.annotations.SubConfiguration;
import commandline.annotations.Toggle;
import commandline.util.AnnotationUtils;

public class OptionSpecificationFactory {

	
	public static OptionSpecification getOptionSpecification(Object spec, Method method) {
		OptionSpecificationBuilder builder = new OptionSpecificationBuilder();
		builder.addMethod(method);
		builder.addConfiguration(spec);
		for (Annotation annotation : method.getAnnotations()) {
			if (annotation instanceof Option) {
			} else if (annotation instanceof LongSwitch) {
				builder.addLongSwitch(((LongSwitch)annotation).value());
			} else if (annotation instanceof ShortSwitch) {
				builder.addShortSwitch(((ShortSwitch)annotation).value());
			} else if (annotation instanceof Toggle) {
				builder.addToggle(((Toggle)annotation).value());
			} else if (annotation instanceof SingleArgument) {
				builder.addSingleArgument();
			} else if (annotation instanceof AllAvailableArguments) {
				builder.addAllAvailableArguments();
			} else if (annotation instanceof ArgumentsUntilDelimiter) {
				builder.addUntilDelimiter(((ArgumentsUntilDelimiter)annotation).value());
			} else if (annotation instanceof SubConfiguration) {
				builder.addSubset(((SubConfiguration)annotation).value());
			} else if (annotation instanceof Required) {
				builder.addRequired();
			} else if (annotation instanceof Multiple) {
				builder.addOccurences(Occurences.MULTIPLE);
			} else {
				// todo
			}
		}
		return builder.getOptionSpecification();
		
	}
	
	public static List<OptionSpecification> getOptionSpecifications(Object spec, Class<? extends Object> clazz) {
		List<Method> methods = AnnotationUtils.getMethodsByAnnotation(clazz, Option.class);
		List<OptionSpecification> optionSpecifications = new ArrayList<OptionSpecification>();
		for (Method method : methods) {
			optionSpecifications.add(getOptionSpecification(spec,method));
		}
		return optionSpecifications;
	}
}