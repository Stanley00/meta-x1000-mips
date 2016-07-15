DESCRIPTION = "Kernel"
HOMEPAGE = "http://nohomepage.org"
SECTION = "kernel"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM ="file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}}-${PV}:"
LINUX_VERSION ?= "3.10.14"

inherit kernel

PV = "${LINUX_VERSION}+git${SRCPV}"

S = "${WORKDIR}/git"

#KBRANCH = "ci20-v3.18"

SRCREV = "6cb86f14573f52ca0db26eaeabcf82d913daf362"
SRC_URI = " git://github.com/yongli3/x1000-kernel.git;protocol=https	file://defconfig file://gcc6.patch"

#SRCREV = "3b440738b5c1adc3ec3ee72ceca799d1b8d264df"
#SRC_URI = "git://github.com/raspberrypi/linux.git;protocol=git;branch=rpi-4.4.y file://defconfig"	

COMPATIBLE_MACHINE = "(halley2)"

EXTRA_OEMAKE += " V=1"

do_compile_prepend () {
	echo "B=${B}"
	mkdir -p ${B}/drivers/net/ethernet/ingenic/jz_mac_v13
}
