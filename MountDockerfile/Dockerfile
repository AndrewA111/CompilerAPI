FROM openjdk
ADD testCompiler /root/compile/testCompiler
ADD testRunner /root/compile/testRunner
WORKDIR /root/compile/testCompiler

ENTRYPOINT cp -a /root/compile/Files/. /root/compile/testRunner/; java QuestionCompiler /root/compile/testRunner/
