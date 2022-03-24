SUMMARY=" AnyIO is a asynchronous compatibility API that allows applications and libraries written against it to run unmodified on asyncio, curio and trio."
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c0a769411d2af7894099e8ff75058c9f"

inherit pypi setuptools3
DEPENDS += " \
	python3-setuptools-scm-native \
	"

SRC_URI[sha256sum] = "a0aeffe2fb1fdf374a8e4b471444f0f3ac4fb9f5a5b542b48824475e0042a5a6"

SRC_URI += " \
	file://0001-setup.cfg-Create-a-patch-to-set-the-pkg-version-corr.patch \
"