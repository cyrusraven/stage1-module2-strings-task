package com.epam.mjc;
import java.util.*;
public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        String methodSignaturePart = signatureString.substring(0, signatureString.indexOf('('));
        List<String> methodSignaturePartList = new StringSplitter().splitByDelimiters(methodSignaturePart, List.of(" "));

        String methodArgumentPart = signatureString.substring(signatureString.indexOf('(') + 1, signatureString.indexOf(')'));
        List<String> methodArgumentPartList = new StringSplitter().splitByDelimiters(methodArgumentPart, List.of(" ", ","));

        List<MethodSignature.Argument> argumentList = new ArrayList<>();
        if (!methodArgumentPartList.isEmpty()) {
            for (int i = 0; i < methodArgumentPartList.size() - 1; i += 2) {
                argumentList.add(new MethodSignature.Argument(methodArgumentPartList.get(i),
                        methodArgumentPartList.get(i + 1)));
            }
        }
        MethodSignature methodSignature;
        if (methodSignaturePartList.size() == 2) {
            methodSignature = new MethodSignature(methodSignaturePartList.get(1), argumentList);
            methodSignature.setReturnType(methodSignaturePartList.get(0));
            return methodSignature;
        }
        if (methodSignaturePartList.size() == 3) {
            methodSignature = new MethodSignature(methodSignaturePartList.get(2), argumentList);
            methodSignature.setAccessModifier(methodSignaturePartList.get(0));
            methodSignature.setReturnType(methodSignaturePartList.get(1));
            return methodSignature;
        }
        return null;
    }
}