#ifdef OPENBW

#include "BWAPILauncher.h"

#include <cstdio>
#include <thread>

#include <jni.h>

#include "BW/BWData.h"
#include "BWAPI/GameImpl.h"

#include "Globals.h"

void Bridge::BWAPILauncher::run(JNIEnv *env) {
  std::thread mainThread([] {
    BW::sacrificeThreadForUI([] {
      while (!Bridge::Globals::finished) std::this_thread::sleep_for(std::chrono::seconds(5));
    });
  });

  try {
    BW::GameOwner gameOwner;

    gameOwner.setPrintTextCallback([](const char *str) {
      std::string s;
      while (*str) {
        char c = *str++;
        if ((unsigned)c >= 0x20 || c == 9 || c == 10 || c == 13) s += c;
      }
      printf("%s\n", s.c_str());
    });

    BW::Game game = gameOwner.getGame();
    BWAPI::BroodwarImpl_handle handle(game);

    do {
      handle->autoMenuManager.startGame();

      while (!handle->bwgame.gameOver()) {
        handle->update();
        handle->bwgame.nextFrame();

        if (!handle->externalModuleConnected) {
          std::cerr << "error: no module loaded, exiting" << std::endl;
          if (env->ExceptionOccurred()) {
            env->ExceptionDescribe();
          }
          return;
        }
      }
      handle->update();
      handle->onGameEnd();
      handle->bwgame.leaveGame();

    } while (!handle->bwgame.gameClosed() && handle->autoMenuManager.autoMenuRestartGame != "" && handle->autoMenuManager.autoMenuRestartGame != "OFF");
  } catch (const std::exception &e) {
    printf("Error: %s\n", e.what());
  }
  Bridge::Globals::finished = true;

  mainThread.join();
}

#endif
