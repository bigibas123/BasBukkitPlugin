package com.github.bigibas123.ServerChangeGui.util;


import com.github.bigibas123.ServerChangeGui.ServerChangeGui;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.logging.Level;

public class LogHelper {

	private final ServerChangeGui plugin;

	public LogHelper(ServerChangeGui plugin) {
		this.plugin = plugin;
	}

	private void log(Level logLevel, Object msg) {

		Bukkit.getLogger().log(logLevel, "[" + plugin.getName() + "] " + msg, plugin);

	}

	public void ALL(Object object) {
		log(Level.ALL, object);
	}

	public void FINEST(Object object) {
		log(Level.FINEST, object);
	}

	public void FINER(Object object) {
		log(Level.FINER, object);
	}

	public void FINE(Object object) {
		log(Level.FINE, object);
	}

	public void CONFIG(Object object) {
		log(Level.CONFIG, object);
	}

	public void INFO(Object object) {
		log(Level.INFO, object);
	}

	public void WARNING(Object object) {
		log(Level.WARNING, object);
	}

	public void SEVERE(Object object) {
		log(Level.SEVERE, object);
	}


	public PrintWriter asStream(Level l) {
		return new PrintWriter(new Writer() {

			private final StringBuilder buf = new StringBuilder();


			@Override
			public void write(@NotNull String str) {
				synchronized (buf) {
					if (!str.contains("\n")) {
						buf.append(str);
					} else {
						String[] strings = str.split("\n");
						if (strings.length <= 0) {
							this.flush();
						} else if (strings.length <= 1) {
							this.buf.append(strings[0]);
						} else {
							for (String s : strings) {
								this.buf.append(s);
								this.flush();
							}
						}
					}

				}
			}

			@Override
			public void write(@NotNull char[] cbuf, int off, int len) {
				synchronized (buf) {
					for (int i = off; i < off + len; i++) {
						if (cbuf[i] == '\n' || cbuf[i] == '\r' || cbuf[i] == '\u0085' || cbuf[i] == '\u2028' || cbuf[i] == '\u2029') {
							this.flush();
						} else {
							this.buf.append(cbuf[i]);
						}
					}
				}
			}

			@Override
			public void flush() {
				synchronized (buf) {
					log(l, buf.toString());
					buf.delete(0, buf.length());
				}
			}

			@Override
			public void close() {
				//NOOP
			}
		});
	}
}
