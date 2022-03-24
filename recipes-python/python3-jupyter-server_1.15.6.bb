SUMMARY = "The Jupyter Server is a web application that allows you to create \
           and share documents that contain live code, equations, \
           visualizations, and explanatory text. The Notebook has support for \
           multiple programming languages, sharing, and interactive widgets."
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING.md;md5=0d99f15eb14ae0f6bd895f65127d0fa8"

PYPI_PACKAGE = "jupyter_server"
PN="python3-jupyter_server"

inherit pypi setuptools3

SRC_URI[sha256sum] = "56bd6f580d1f46b62294990e8e78651025729f5d3fc798f10f2c03f0cdcbf28d"

do_install_append() {
	# this files will be installed by python3-notebook
	rm -f ${D}${bindir}/jupyter-bundlerextension
}

RDEPENDS_${PN} += " \
	python3-jinja2 \
	python3-tornado \
	python3-pyzmq \
	python3-ipython-genutils \
	python3-traitlets \
	python3-jupyter-core \
	python3-jupyter-client \
	python3-nbformat \
	python3-nbconvert \
	python3-send2trash \
	python3-terminado \
	python3-prometheus-client \
	"
