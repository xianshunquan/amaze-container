# amaze-container
单进程多模块启动

设计初衷：
针对重要性低，性能要求不高的独立模块服务，若不希望每个独立模块部署一个服务，可以放置在一个进程里面，由进程里面的模块管理容器来管理模块，
模块共享容器的资源，模块可以独立重启和关闭，可以独立发布。
