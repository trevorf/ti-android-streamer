/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2011-2013 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

/** This is generated, do not edit by hand. **/

#include <jni.h>

#include "Proxy.h"

		namespace com {
		namespace woohoo {
		namespace androidaudiostreamer {


class AndroidaudiostreamerModule : public titanium::Proxy
{
public:
	explicit AndroidaudiostreamerModule(jobject javaObject);

	static void bindProxy(v8::Handle<v8::Object> exports);
	static v8::Handle<v8::FunctionTemplate> getProxyTemplate();
	static void dispose();

	static v8::Persistent<v8::FunctionTemplate> proxyTemplate;
	static jclass javaClass;

private:
	// Methods -----------------------------------------------------------
	static v8::Handle<v8::Value> getStatus(const v8::Arguments&);
	static v8::Handle<v8::Value> stop(const v8::Arguments&);
	static v8::Handle<v8::Value> getMetaGenre(const v8::Arguments&);
	static v8::Handle<v8::Value> setAllowBackground(const v8::Arguments&);
	static v8::Handle<v8::Value> getMetaTitle(const v8::Arguments&);
	static v8::Handle<v8::Value> volume(const v8::Arguments&);
	static v8::Handle<v8::Value> play(const v8::Arguments&);
	static v8::Handle<v8::Value> getMaxVolume(const v8::Arguments&);
	static v8::Handle<v8::Value> getMetaUrl(const v8::Arguments&);
	static v8::Handle<v8::Value> getCurrentVolume(const v8::Arguments&);

	// Dynamic property accessors ----------------------------------------

};

		} // androidaudiostreamer
		} // woohoo
		} // com
