require recipes-bsp/u-boot/u-boot.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# No patches for other machines yet
COMPATIBLE_MACHINE = "halley2"

SRC_URI = "git://github.com/yongli3/x1000-u-boot.git;protocol=https"

SRC_URI += "file://gcc6.patch file://gcc5.patch"

SRCREV = "81a8603e854a206c7091733207748d60155aaf92"

PV = "v2013.07+git${SRCPV}"

LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

S = "${WORKDIR}/git"

TARGET_LDFLAGS=""
UBOOT_EXT = "bin"

PARALLEL_MAKE=""

UBOOT_MACHINE ?= "halley2_v10_uImage_sfc_nor"
UBOOT_BINARY = "u-boot.${UBOOT_EXT}"
UBOOT_IMAGE = "u-boot-${MACHINE}-${PV}-${PR}.${UBOOT_EXT}"
UBOOT_SYMLINK = "u-boot-${MACHINE}.${UBOOT_EXT}"
UBOOT_MAKE_TARGET = "all"
UBOOT_SPL = "u-boot-with-spl.bin"
UBOOT_SPL_IMAGE = "u-boot-${MACHINE}-${PV}-${PR}-spl.bin"

do_configure () {
    oe_runmake  ${UBOOT_MACHINE}
}

do_compile () {
    oe_runmake ${UBOOT_MACHINE}
}

do_install () {
	install -d ${D}/boot
#	install ${S}/${UBOOT_BINARY} ${D}/boot/${UBOOT_IMAGE}
#	ln -sf ${UBOOT_IMAGE} ${D}/boot/${UBOOT_BINARY}

	install ${S}/${UBOOT_SPL} ${D}/boot/${UBOOT_SPL_IMAGE}
	ln -sf ${UBOOT_SPL} ${D}/boot/${UBOOT_SPL_IMAGE}

#	if [ -e ${WORKDIR}/fw_env.config ] ; then
#		install -d ${D}${sysconfdir}
#		install -m 644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
#	fi

}

FILES_${PN} = "/boot ${sysconfdir}"
# no gnu_hash in uboot.bin, by design, so skip QA
INSANE_SKIP_${PN} = "1"

inherit deploy

addtask deploy before do_package after do_compile

do_deploy () {
	install -d ${DEPLOYDIR}
#	install ${S}/${UBOOT_BINARY} ${DEPLOYDIR}/${UBOOT_IMAGE}
	install ${S}/${UBOOT_SPL} ${DEPLOYDIR}/${UBOOT_SPL_IMAGE}

#	cd ${DEPLOYDIR}
#	rm -f ${UBOOT_BINARY} ${UBOOT_SYMLINK}
#	ln -sf ${UBOOT_IMAGE} ${UBOOT_SYMLINK}
#	ln -sf ${UBOOT_IMAGE} ${UBOOT_BINARY}
	
#	rm -f ${UBOOT_SPL}
#	ln -sf ${UBOOT_SPL_IMAGE} ${UBOOT_SPL}
}
