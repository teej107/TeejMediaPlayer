package com.teej107.mediaplayer.io.uninstaller;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.util.Response;
import com.teej107.mediaplayer.util.Util;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by teej107 on 5/31/17.
 */
public class FileUninstaller extends AbstractUninstaller
{
	protected Path path;

	public FileUninstaller(Application application, Path path, String name)
	{
		super(application, name);
		this.path = path;
	}

	@Override
	public Response uninstall() throws IOException
	{
		if(!Files.isDirectory(path))
			return Util.delete(path);

		List<String> deleteFail = new ArrayList<>();
		Files.walk(path).sorted(Comparator.reverseOrder()).forEach(path ->
		{
			if(Util.delete(path).getStatus() != Response.OK && !Files.isDirectory(path))
			{
				deleteFail.add(path.toString());
			}
		});
		Response response;
		if(deleteFail.size() == 0)
		{
			response = Response.createOkResponse();
		}
		else
		{
			response = Response.createErrorResponse(deleteFail.toArray(new String[deleteFail.size()]));
		}
		return response;
	}
}
