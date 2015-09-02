/* C++ code produced by gperf version 3.0.3 */
/* Command-line: /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/gperf -L C++ -E -t /private/var/folders/w0/716fh0257xb79k1ny7z9nt_w0000gn/T/trevorfifield/androidaudiostreamer-generated/KrollGeneratedBindings.gperf  */
/* Computed positions: -k'' */

#line 3 "/private/var/folders/w0/716fh0257xb79k1ny7z9nt_w0000gn/T/trevorfifield/androidaudiostreamer-generated/KrollGeneratedBindings.gperf"


#include <string.h>
#include <v8.h>
#include <KrollBindings.h>

#include "com.woohoo.androidaudiostreamer.AndroidaudiostreamerModule.h"


#line 13 "/private/var/folders/w0/716fh0257xb79k1ny7z9nt_w0000gn/T/trevorfifield/androidaudiostreamer-generated/KrollGeneratedBindings.gperf"
struct titanium::bindings::BindEntry;
/* maximum key range = 1, duplicates = 0 */

class AndroidaudiostreamerBindings
{
private:
  static inline unsigned int hash (const char *str, unsigned int len);
public:
  static struct titanium::bindings::BindEntry *lookupGeneratedInit (const char *str, unsigned int len);
};

inline /*ARGSUSED*/
unsigned int
AndroidaudiostreamerBindings::hash (register const char *str, register unsigned int len)
{
  return len;
}

struct titanium::bindings::BindEntry *
AndroidaudiostreamerBindings::lookupGeneratedInit (register const char *str, register unsigned int len)
{
  enum
    {
      TOTAL_KEYWORDS = 1,
      MIN_WORD_LENGTH = 58,
      MAX_WORD_LENGTH = 58,
      MIN_HASH_VALUE = 58,
      MAX_HASH_VALUE = 58
    };

  static struct titanium::bindings::BindEntry wordlist[] =
    {
      {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""},
      {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""},
      {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""},
      {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""},
      {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""},
      {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""},
      {""}, {""}, {""}, {""},
#line 15 "/private/var/folders/w0/716fh0257xb79k1ny7z9nt_w0000gn/T/trevorfifield/androidaudiostreamer-generated/KrollGeneratedBindings.gperf"
      {"com.woohoo.androidaudiostreamer.AndroidaudiostreamerModule", ::com::woohoo::androidaudiostreamer::AndroidaudiostreamerModule::bindProxy, ::com::woohoo::androidaudiostreamer::AndroidaudiostreamerModule::dispose}
    };

  if (len <= MAX_WORD_LENGTH && len >= MIN_WORD_LENGTH)
    {
      register int key = hash (str, len);

      if (key <= MAX_HASH_VALUE && key >= 0)
        {
          register const char *s = wordlist[key].name;

          if (*str == *s && !strcmp (str + 1, s + 1))
            return &wordlist[key];
        }
    }
  return 0;
}
#line 16 "/private/var/folders/w0/716fh0257xb79k1ny7z9nt_w0000gn/T/trevorfifield/androidaudiostreamer-generated/KrollGeneratedBindings.gperf"

