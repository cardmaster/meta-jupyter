SUMMARY = "JupyterLab Server"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://PKG-INFO;beginline=8;endline=8;md5=23f9ad5cad3d8cc0336e2a5d8a87e1fa"

PYPI_PACKAGE = "jupyterlab_server"
PN="python3-jupyterlab_server"

inherit pypi setuptools3

do_compile() {
        cd ${S}
        NO_FETCH_BUILD=1 \
        STAGING_INCDIR=${STAGING_INCDIR} \
        STAGING_LIBDIR=${STAGING_LIBDIR} \
        PYTHONPATH=${STAGING_DIR_TARGET}/${PYTHON_SITEPACKAGES_DIR} \
        ${STAGING_BINDIR_NATIVE}/${PYTHON_PN}-native/${PYTHON_PN} ${S}/setup.py \
        build --build-base=${B} ${DISTUTILS_BUILD_ARGS} || \
        bbfatal_log "'${PYTHON_PN} setup.py build ${DISTUTILS_BUILD_ARGS}' execution failed."
}

do_install() {
        cd ${S}
        install -d ${D}${PYTHON_SITEPACKAGES_DIR}
        STAGING_INCDIR=${STAGING_INCDIR} \
        STAGING_LIBDIR=${STAGING_LIBDIR} \
        PYTHONPATH=${D}${PYTHON_SITEPACKAGES_DIR}:${STAGING_DIR_TARGET}/${PYTHON_SITEPACKAGES_DIR} \
        ${STAGING_BINDIR_NATIVE}/${PYTHON_PN}-native/${PYTHON_PN} ${S}/setup.py \
        build --build-base=${B} install --skip-build ${DISTUTILS_INSTALL_ARGS} || \
        bbfatal_log "'${PYTHON_PN} setup.py install ${DISTUTILS_INSTALL_ARGS}' execution failed."

        # support filenames with *spaces*
        find ${D} -name "*.py" -exec grep -q ${D} {} \; \
                               -exec sed -i -e s:${D}::g {} \;

        for i in ${D}${bindir}/* ${D}${sbindir}/*; do
            if [ -f "$i" ]; then
                sed -i -e s:${PYTHON}:${USRBINPATH}/env\ ${DISTUTILS_PYTHON}:g $i
                sed -i -e s:${STAGING_BINDIR_NATIVE}:${bindir}:g $i
            fi
        done

        rm -f ${D}${PYTHON_SITEPACKAGES_DIR}/easy-install.pth

        #
        # FIXME: Bandaid against wrong datadir computation
        #
        if [ -e ${D}${datadir}/share ]; then
            mv -f ${D}${datadir}/share/* ${D}${datadir}/
            rmdir ${D}${datadir}/share
        fi
}

DEPENDS += " \
        python3-jupyter_server \
        python3-jinja2 \
        python3-markupsafe \
        python3-jupyter-core \
        python3-traitlets \
        python3-ipython-genutils \
        python3-tornado \
        python3-nbformat \
        python3-prometheus-client \
        python3-packaging-native \
        python3-jupyter-client-native \
        python3-jsonschema \
        python3-attrs \
        python3-pyrsistent \
        python3-anyio \
        python3-sniffio \
        python3-send2trash \
        python3-websocket-client \
"

RDEPENDS_${PN} += " \
    python3-babel \
    python3-entrypoints \
    python3-jinja2 \
    python3-json5 \
    python3-jsonschema \
    python3-requests \
    python3-jupyter_server \
    python3-packaging \
"

SRC_URI[sha256sum] = "5d8dc70f6803dc48efb69fb43e3cd2f8c6aad4ba011670318e5efd26c7487bb9"