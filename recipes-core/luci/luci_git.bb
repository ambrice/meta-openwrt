# Copyright (C) 2015 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "OpenWrt LuCI web user interface"
HOMEPAGE = "https://github.com/openwrt/luci"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2b42edef8fa55315f34f2370b4715ca9"
SECTION = "base"
DEPENDS = "json-c libubox lua5.1"
RDEPENDS_${PN} = "lua5.1"

SRCREV = "5b79e62c0a99bab8dfb8dce8124d9fecc11da54b"

SRC_URI = "git://github.com/openwrt/luci.git"

inherit pkgconfig openwrt

S = "${WORKDIR}/git"
EXTRA_OEMAKE += "TOPDIR=${S}/openwrt"

FILES_${PN} = "/www \
               /usr/share \
               /sbin \
               /etc/config \
"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
	cp -a ${S}/modules/luci-base/htdocs ${D}
	mv ${D}/htdocs ${D}/www
	chown -R root:root ${D}/www

	install -Dm 0644 ${S}/modules/luci-base/root/www/index.html ${D}/www/index.html

	install -Dm 0644 ${S}/modules/luci-base/root/etc/config/ucitrack ${D}${sysconfdir}/config/ucitrack
	install -Dm 0644 ${S}/modules/luci-base/root/etc/config/luci ${D}${sysconfdir}/config/luci

	install -Dm 0755 ${S}/modules/luci-base/root/sbin/luci-reload ${D}/sbin/luci-reload
	install -Dm 0644 ${S}/modules/luci-base/root/usr/share/acl.d/luci-base.json ${D}/usr/share/acl.d/luci-base.json
}
