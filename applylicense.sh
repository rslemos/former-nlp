#!/bin/sh
copyrightNotice() cat << EOF
BEGIN COPYRIGHT NOTICE

This file is part of program "Natural Language Processing"
Copyright 2011  Rodrigo Lemos

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

END COPYRIGHT NOTICE
EOF

NOTICEMARKERSREGEXP="\(BEGIN\|END\) COPYRIGHT NOTICE"

HASHNOTICE="`mktemp -t noticeXXXXX`"
JAVANOTICE="`mktemp -t noticeXXXXX`"
XMLNOTICE="`mktemp -t noticeXXXXX`"

trap "rm -fR $HASHNOTICE $XMLNOTICE $JAVANOTICE" exit

(
	copyrightNotice | sed -e 's/^/# /'
) > "$HASHNOTICE"

(
	echo "<!--"
	copyrightNotice | sed -e 's/^/  /'
	echo "-->"
) > "$XMLNOTICE"

(
	head -c 80 < /dev/zero | tr '\0' '*' | sed -e 's/^*/\//' -e 's/$/\n/'
	copyrightNotice | sed -e 's/^/ * /'
	head -c 80 < /dev/zero | tr '\0' '*' | sed -e 's/^*/ /' -e 's/*$/\/\n/'
) > "$JAVANOTICE"

findPreviousLicense() {
	FILE="$1"
	GREPOUTPUT=`grep "$NOTICEMARKERSREGEXP" -Zno "$FILE"` || return 1;
	echo "$GREPOUTPUT" | sed -e "s/:$NOTICEMARKERSREGEXP//g" | tr "\n" " "
}

stripPreviousLicense() {
	FILE="$1"
	INCRBEGIN="$2"
	INCREND="$3"

	LINES="`findPreviousLicense "$FILE"`" || return
	set -- $LINES; BEGIN="$1"; END="$2"

	BEGIN="$(($BEGIN $INCRBEGIN))"
	END="$(($END $INCREND))"

	sed -e "${BEGIN},${END}d" -i "$FILE"
}

stuffFirstLine() {
	FILE="$1"
	sed -i "$FILE" -f - << EOF
1i\
_
EOF
}

applyJava() {
	FILE="$1"
	stripPreviousLicense "$FILE" "-1" "+1"

	stuffFirstLine "$FILE"
	sed -i "$FILE" -e "1r $JAVANOTICE" -e "1d"
}

applyXML() {
	FILE="$1"
	stripPreviousLicense "$FILE" "-1" "+1"

	# aaa aaa dd dd:dd:dd aaa dddd
	if (head -n 1 "$FILE" | grep -q "<?") then
		sed -i "$FILE" -e "1r $XMLNOTICE"
	else
		stuffFirstLine "$FILE"
		sed -i "$FILE" -e "1r $XMLNOTICE" -e "1d"
	fi
}

applyHash() {
	FILE="$1"
	stripPreviousLicense "$FILE"

	# aaa aaa dd dd:dd:dd aaa dddd
	if (head -n 1 "$FILE" | grep -q "^#... ... .. ..:..:.. ..S\?. ....$") then
		sed -i "$FILE" -e "1r $HASHNOTICE"
	elif (head -n 1 "$FILE" | grep -q "^#!") then
		sed -i "$FILE" -e "1r $HASHNOTICE"
	else
		stuffFirstLine "$FILE"
		sed -i "$FILE" -e "1r $HASHNOTICE" -e "1d"
	fi
}

find -type f ! -path './.git/*'  ! -path '*/target/*' -print |
while read FILE
do
	case "`basename "$FILE"`" in
		*.java | *.aj)
			applyJava "$FILE"
			;;
		*.xml | *.xsd)
			applyXML "$FILE"
			;;
		*.ad | COPYING | applylicense.sh )
			# ignore
			;;
		*)
			MAGIC="`file -b "$FILE"`"
			case "$MAGIC" in
				"XML  document text")
					applyXML "$FILE"
					;;
				"ASCII text" | "POSIX shell script text executable")
					applyHash "$FILE"
					;;
				*)
					# don't know
					echo "$FILE: $MAGIC"
					;;
			esac
			;;
	esac
done

